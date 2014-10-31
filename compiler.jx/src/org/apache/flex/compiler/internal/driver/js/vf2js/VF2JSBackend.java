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

package org.apache.flex.compiler.internal.driver.js.vf2js;

import java.io.FilterWriter;

import org.apache.flex.compiler.codegen.js.IJSEmitter;
import org.apache.flex.compiler.internal.codegen.js.vf2js.JSVF2JSEmitter;
import org.apache.flex.compiler.internal.driver.js.goog.GoogBackend;
import org.apache.flex.compiler.internal.targets.FlexJSTarget;
import org.apache.flex.compiler.internal.targets.JSTarget;
import org.apache.flex.compiler.projects.IASProject;
import org.apache.flex.compiler.targets.ITargetProgressMonitor;
import org.apache.flex.compiler.targets.ITargetSettings;

/**
 * @author Erik de Bruin
 */
public class VF2JSBackend extends GoogBackend
{

    @Override
    public IJSEmitter createEmitter(FilterWriter out)
    {
        IJSEmitter emitter = new JSVF2JSEmitter(out);
        emitter.setDocEmitter(createDocEmitter(emitter));
        return emitter;
    }
    
    @Override
    public JSTarget createTarget(IASProject project, ITargetSettings settings,
            ITargetProgressMonitor monitor)
    {
        return new FlexJSTarget(project, settings, monitor);
    }


}
