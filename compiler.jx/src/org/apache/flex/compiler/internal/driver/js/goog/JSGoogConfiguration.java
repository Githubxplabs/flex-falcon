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

package org.apache.flex.compiler.internal.driver.js.goog;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.flex.compiler.clients.JSConfiguration;
import org.apache.flex.compiler.clients.MXMLJSC;
import org.apache.flex.compiler.config.ConfigurationValue;
import org.apache.flex.compiler.exceptions.ConfigurationException;
import org.apache.flex.compiler.internal.config.annotations.Config;
import org.apache.flex.compiler.internal.config.annotations.FlexOnly;
import org.apache.flex.compiler.internal.config.annotations.InfiniteArguments;
import org.apache.flex.compiler.internal.config.annotations.Mapping;

/**
 * The {@link JSGoogConfiguration} class holds all compiler arguments needed for
 * compiling ActionScript to JavaScript the 'goog' way.
 * <p>
 * Specific flags are implemented here for the configuration to be loaded by the
 * configure() method of {@link MXMLJSC}.
 * <p>
 * This class inherits all compiler arguments from the MXMLC compiler.
 * 
 * @author Erik de Bruin
 */
public class JSGoogConfiguration extends JSConfiguration
{
    public JSGoogConfiguration()
    {
    }

    //
    // 'closure-lib'
    //

    protected String closureLib = "";

    public String getClosureLib()
    {
        try
        {
            if (closureLib.equals(""))
            {
                closureLib = getAbsolutePathFromPathRelativeToMXMLC(
                        "../../js/lib/google/closure-library");
            }
        }
        catch (Exception e) { /* better to try and fail... */ }
        
        return closureLib;
    }

    @Config
    @Mapping("closure-lib")
    public void setClosureLib(ConfigurationValue cv, String value)
            throws ConfigurationException
    {
        if (value != null)
            closureLib = value;
    }

    //
    // Override 'compiler.binding-value-change-event-type'
    //

    private String bindingValueChangeEventType = "valueChange";

    @Override
    public String getBindingValueChangeEventType()
    {
        return bindingValueChangeEventType;
    }

    @Override
    @Config(advanced = true)
    public void setCompilerBindingValueChangeEventType(ConfigurationValue cv, String b)
    {
        bindingValueChangeEventType = b;
    }

    //
    // Override 'compiler.mxml.children-as-data'
    //
    
    private Boolean childrenAsData = true;
    
    @Override
    public Boolean getCompilerMxmlChildrenAsData()
    {
        return childrenAsData;
    }

    @Override
    @Config
    @Mapping({"compiler", "mxml", "children-as-data"})
    @FlexOnly
    public void setCompilerMxmlChildrenAsData(ConfigurationValue cv, Boolean asData) throws ConfigurationException
    {
        childrenAsData = asData;
    }

    //
    // 'marmotinni'
    //

    private String marmotinni;

    public String getMarmotinni()
    {
        return marmotinni;
    }

    @Config
    @Mapping("marmotinni")
    public void setMarmotinni(ConfigurationValue cv, String value)
            throws ConfigurationException
    {
        marmotinni = value;
    }

    //
    // 'sdk-js-lib'
    //

    protected List<String> sdkJSLib = new ArrayList<String>();

    public List<String> getSDKJSLib()
    {
        if (sdkJSLib.size() == 0)
        {
            try
            {
                String path = getAbsolutePathFromPathRelativeToMXMLC(
                            "../../frameworks/js/FlexJS/src");

                sdkJSLib.add(path);
            }
            catch (Exception e) { /* better to try and fail... */ }
        }
        
        return sdkJSLib;
    }

    @Config(allowMultiple = true)
    @Mapping("sdk-js-lib")
    @InfiniteArguments
    public void setSDKJSLib(ConfigurationValue cv, List<String> value)
            throws ConfigurationException
    {
        sdkJSLib.addAll(value);
    }

    //
    // 'external-js-lib'
    //

    private List<String> externalJSLib = new ArrayList<String>();

    public List<String> getExternalJSLib()
    {
        return externalJSLib;
    }

    @Config(allowMultiple = true)
    @Mapping("external-js-lib")
    @InfiniteArguments
    public void setExternalJSLib(ConfigurationValue cv, List<String> value)
            throws ConfigurationException
    {
        externalJSLib.addAll(value);
    }

    //
    // 'strict-publish'
    //

    private boolean strictPublish = true;

    public boolean getStrictPublish()
    {
        return strictPublish;
    }

    @Config
    @Mapping("strict-publish")
    public void setStrictPublish(ConfigurationValue cv, boolean value)
            throws ConfigurationException
    {
        strictPublish = value;
    }

    //
    // 'keep-asdoc'
    //

    private boolean keepASDoc = true;

    public boolean getKeepASDoc()
    {
        return keepASDoc;
    }

    @Config
    @Mapping("keep-asdoc")
    public void setKeepASDoc(ConfigurationValue cv, boolean value)
            throws ConfigurationException
    {
    	keepASDoc = value;
    }

    
    
    protected String getAbsolutePathFromPathRelativeToMXMLC(String relativePath)
        throws IOException
    {
        String mxmlcURL = MXMLJSC.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();

        File mxmlc = new File(URLDecoder.decode(mxmlcURL, "utf-8"));
        
        return new File(mxmlc.getParent() + File.separator + relativePath)
                .getCanonicalPath();
    }

}
