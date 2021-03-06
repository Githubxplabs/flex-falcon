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

package as;

import org.junit.Test;

/**
 * Feature tests for AS Namespaces.
 */
public class ASKeywordTests extends ASFeatureTestsBase
{
    @Test
    public void ASKeyword_SwitchStatement()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function hasSwitch(switcher:int):Boolean {",
            "    trace('hey, a method named namespace worked');",
            "    switch (switcher) {",
            "       case 1:",
            "           foo = 'bar';",
            "           break;",
            "       default:",
            "           foo = 'baz';",
            "           break;",
            "    }",
            "    return true;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"hasSwitch(1);",
            "assertEqual('switch worked', foo, 'bar');",
        	"hasSwitch(0);",
            "assertEqual('switch worked', foo, 'baz');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_Namespace_as_method_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function namespace(instance:Object):Boolean {",
            "    trace('hey, a method named namespace worked');",
            "    foo = 'as';",
            "    return true;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"namespace(this);",
            "assertEqual('method named namespace', foo, 'as');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_Default_as_method_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function default(instance:Object):Boolean {",
            "    trace('hey, a method named default worked');",
            "    foo = 'as';",
            "    return true;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"default(this);",
            "assertEqual('method named default', foo, 'as');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_for_as_method_name()
    {
        // all tests can assume that flash.display.Sprite
        // flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
                "public var foo:String;",
                "public function for(instance:Object):Boolean {",
                "    trace('hey, a method named default worked');",
                "    foo = 'as';",
                "    return true;",
                "}",
        };
        String[] testCode = new String[]
        {
                "this.for(this);",
                "assertEqual('method named default', foo, 'as');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_Get_as_method_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function get(instance:Object):Boolean {",
            "    trace('hey, a method named get worked');",
            "    foo = 'as';",
            "    return true;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"get(this);",
            "assertEqual('method named get', foo, 'as');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_Get_as_getter_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function get get():Boolean {",
            "    trace('hey, a getter named get worked');",
            "    foo = 'as';",
            "    return true;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"var bar:Boolean = get;",
            "assertEqual('getter named get', foo, 'as');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_Get_as_member_expression_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function get get():Boolean {",
            "    trace('hey, a getter named get worked');",
            "    foo = 'as';",
            "    return true;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"var bar:Boolean = this.get;",
            "assertEqual('getter named get', foo, 'as');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_as_method_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function as(instance:Object):Boolean {",
            "    trace('hey, a method named as worked');",
            "    foo = 'as';",
            "    return true;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"as(this);",
            "assertEqual('method named as', foo, 'as');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_as_property_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var foo:String;",
            "public function get as():String {",
            "    return foo;",
            "}",
            "public function set as(value:String):void {",
            "    foo = value;",
            "}",
        };
        String[] testCode = new String[]
        {
        	"as = 'bar';",
            "assertEqual('property named as', as, 'bar');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }
    
    @Test
    public void ASKeyword_as_variable_name()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var as:String;",
        };
        String[] testCode = new String[]
        {
        	"as = 'bar';",
            "assertEqual('variable named as', as, 'bar');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_default_as_variable_name()
    {
        // all tests can assume that flash.display.Sprite
        // flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
                "public var default:String;",
        };
        String[] testCode = new String[]
        {
                "default = 'bar';",
                "assertEqual('variable named default', default, 'bar');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_include_as_variable_name()
    {
        // all tests can assume that flash.display.Sprite
        // flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
                "public var include:String;",
        };
        String[] testCode = new String[]
        {
                "this.include = 'bar';",
                "assertEqual('variable named include', this.include, 'bar');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void ASKeyword_as_member_expression()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
            "public var as:String;",
        };
        String[] testCode = new String[]
        {
        	"this.as = 'bar';",
            "assertEqual('variable named as', this.as, 'bar');",
        };
        String source = getAS(imports, declarations, testCode, new String[0]);
        compileAndRun(source);
    }

    @Test
    public void AS_new_function_returned_from_function()
    {
    	// all tests can assume that flash.display.Sprite
    	// flash.system.System and flash.events.Event have been imported
        String[] imports = new String[]
        {
        };
        String[] declarations = new String[]
        {
        };
        String[] testCode = new String[]
        {
            "function getClass(index:Number):Class {",
            "  if (index == 0) return Inner;",
            "  return Number;",
            "}",
        	"var foo:Inner = new getClass(0)('foo');",
            "assertEqual('foo.value', foo.value, 'foo');",
        };
        String[] extra = new String[]
        {
        	"class Inner {",
        	"    public function Inner(value:String) {",
        	"        this.value = value;",
        	"    }",
        	"    public var value:String;",
        	"}",
        };
        String source = getAS(imports, declarations, testCode, extra);
        compileAndRun(source);
    }
}
