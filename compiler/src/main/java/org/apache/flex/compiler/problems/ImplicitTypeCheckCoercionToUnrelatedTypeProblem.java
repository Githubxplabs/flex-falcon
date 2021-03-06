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

package org.apache.flex.compiler.problems;

import org.apache.flex.compiler.tree.as.IASNode;

/**
 *  Diagnostic emitted when the method body semantic checker detects
 *  a coercion from a type to an unrelated type in a 'is' or 'as' binop.
 *  
 *  This is only different to {@link ImplicitCoercionToUnrelatedTypeProblem} in that
 *  this is a SemanticProblem rather than a StrictSemanticProblem, is/as binop should
 *  always report this failure, but other cases only in strict mode.
 */
public final class ImplicitTypeCheckCoercionToUnrelatedTypeProblem extends SemanticProblem
{
    public static final String DESCRIPTION =
        "Implicit coercion of a value of type ${actualType} to an unrelated type ${expectedType}.";

    public static final int errorCode = 5031;

    public ImplicitTypeCheckCoercionToUnrelatedTypeProblem(IASNode site, String actualType, String expectedType)
    {
        super(site);
        this.actualType = actualType;
        this.expectedType = expectedType;
    }

    public final String actualType;
    public final String expectedType;
}