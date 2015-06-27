/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.apache.flex.compiler.internal.codegen.externals.reference;

import java.util.Set;

import org.apache.flex.compiler.clients.ExternCConfiguration.ExcludedMemeber;
import org.apache.flex.compiler.internal.codegen.externals.utils.FunctionUtils;

import com.google.javascript.rhino.JSDocInfo;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.jstype.JSType;

public class MethodReference extends MemberReference
{

    private boolean isStatic;
    private MethodReference override;
    private Node paramNode;

    private MethodReference getContext()
    {
        return override == null ? this : override;
    }

    public boolean isStatic()
    {
        return isStatic;
    }

    public void setStatic(boolean isStatic)
    {
        this.isStatic = isStatic;
    }

    public Set<String> getParameterNames()
    {
        return getComment().getParameterNames();
    }

    public String toReturnTypeAnnotationString()
    {
        JSType jsType = getModel().evaluate(getComment().getReturnType());
        return jsType.toAnnotationString();
    }

    public MethodReference(ReferenceModel model, ClassReference classReference,
            Node node, String name, JSDocInfo comment, boolean isStatic)
    {
        super(model, classReference, node, name, comment);
        this.isStatic = isStatic;

        if (node.isFunction())
        {
            this.paramNode = node.getChildAtIndex(1);
        }
        else if (node.getLastChild().isFunction())
        {
            this.paramNode = node.getLastChild().getChildAtIndex(1);
        }
    }

    @Override
    public void emit(StringBuilder sb)
    {
        // XXX HACK TEMP!
        if (getComment().isConstructor()
                && !getBaseName().equals(getClassReference().getBaseName()))
            return;

        if (isConstructor())
        {
            emitConstructor(sb);
            return;
        }

        if (getClassReference().hasSuperMethod(getQualifiedName()))
            return;

        emitComment(sb);

        ExcludedMemeber excluded = isExcluded();
        if (excluded != null)
        {
            excluded.print(sb);
        }

        emitCode(sb);

        override = null;
    }

    public void emitCode(StringBuilder sb)
    {
        String staticValue = (isStatic) ? "static " : "";
        if (getClassReference().isInterface())
            staticValue = "";

        String isOverride = "";

        if (!getClassReference().isInterface())
        {
            MethodReference overrideFromInterface = getClassReference().getMethodOverrideFromInterface(
                    this);
            if (/*isOverride() && */overrideFromInterface != null)
            {
                override = overrideFromInterface;
            }
        }

        String publicModifier = "";
        String braces = "";
        String returns = "";

        if (!transformReturnString().equals("void"))
        {
            returns = " return null;";
        }

        if (!getClassReference().isInterface())
        {
            publicModifier = "public ";
            braces = " { " + returns + " }";
        }

        sb.append(indent);
        sb.append(publicModifier);
        sb.append(isOverride);
        sb.append(staticValue);
        sb.append("function ");
        sb.append(getQualifiedName());
        sb.append(toPrameterString());
        sb.append(":");
        sb.append(transformReturnString());
        sb.append(braces);
        sb.append("\n");
    }

    private void emitConstructor(StringBuilder sb)
    {
        emitComment(sb);

        sb.append(indent);
        sb.append("public function ");
        sb.append(getBaseName());
        if (!getBaseName().equals("Object"))
        {
            sb.append(toPrameterString());
            sb.append(" {\n");
            sb.append(indent);
            emitSuperCall(sb);
            sb.append(indent);
            sb.append("}");
        }
        else
        {
            sb.append("() {}");
        }

        sb.append("\n");
    }

    private void emitSuperCall(StringBuilder sb)
    {

        sb.append(indent);
        sb.append("super(");

        ClassReference superClass = getClassReference().getSuperClass();
        if (superClass != null && !superClass.getBaseName().equals("Object"))
        {
            MethodReference constructor = superClass.getConstructor();
            Set<String> parameterNames = constructor.getParameterNames();
            int len = parameterNames.size();
            for (int i = 0; i < len; i++)
            {
                sb.append("null");
                if (i < len - 1)
                    sb.append(", ");
            }
        }

        sb.append(");\n");
    }

    public boolean isConstructor()
    {
        return getComment().isConstructor();
    }

    private String transformReturnString()
    {
        return FunctionUtils.toReturnString(getContext(),
                getContext().getComment());
    }

    private String toPrameterString()
    {
        return FunctionUtils.toPrameterString(getContext(),
                getContext().getComment(), paramNode);
    }

    public boolean isOverride()
    {
        return getComment().isOverride();
    }

    @Override
    protected void emitCommentBody(StringBuilder sb)
    {
        emitFunctionCommentBody(sb);
    }
}