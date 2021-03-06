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

package org.apache.flex.compiler.internal.codegen.js.flexjs;

import org.apache.flex.compiler.driver.IBackend;
import org.apache.flex.compiler.internal.codegen.js.goog.TestGoogClass;
import org.apache.flex.compiler.internal.driver.js.flexjs.FlexJSBackend;
import org.apache.flex.compiler.tree.as.IClassNode;
import org.junit.Test;

/**
 * @author Erik de Bruin
 */
public class TestFlexJSClass extends TestGoogClass
{

    @Override
    @Test
    public void testConstructor_super()
    {
        IClassNode node = getClassNode("public class A {public function A() { super(); }}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n  ;\n};");
    }

    @Override
    @Test
    public void testSimpleExtends()
    {
        IClassNode node = getClassNode("public class A extends EventTarget {public function A() {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n */\norg.apache.flex.A = function() {\n  org.apache.flex.A.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Test
    public void testSimpleExtendsWithArgs()
    {
        IClassNode node = getClassNode("public class A extends EventTarget {public function A(arg:String) { super(arg);}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @param {string} arg\n */\norg.apache.flex.A = function(arg) {\n  org.apache.flex.A.base(this, 'constructor', arg);\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Test
    public void testSimpleExtendsWithArgsImplicitSuper()
    {
        IClassNode node = getClassNode("public class A extends EventTarget {public function A(arg:String) {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @param {string} arg\n */\norg.apache.flex.A = function(arg) {\n  org.apache.flex.A.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Override
    @Test
    public void testSimpleExtendsImplements()
    {
        IClassNode node = getClassNode("public class A extends EventTarget implements IEventDispatcher {public function A() {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @implements {flash.events.IEventDispatcher}\n */\norg.apache.flex.A = function() {\n  org.apache.flex.A.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Override
    @Test
    public void testSimpleExtendsImplementsMultiple()
    {
        IClassNode node = getClassNode("public class A extends EventTarget implements IEventDispatcher, ListenableKey {public function A() {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @implements {flash.events.IEventDispatcher}\n * @implements {goog.events.ListenableKey}\n */\norg.apache.flex.A = function() {\n  org.apache.flex.A.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Override
    @Test
    public void testSimpleFinalExtendsImplementsMultiple()
    {
        IClassNode node = getClassNode("public final class A extends EventTarget implements IEventDispatcher, ListenableKey {public function A() {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @implements {flash.events.IEventDispatcher}\n * @implements {goog.events.ListenableKey}\n */\norg.apache.flex.A = function() {\n  org.apache.flex.A.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Override
    @Test
    public void testQualifiedExtendsImplementsMultiple()
    {
        IClassNode node = getClassNode("public class A extends goog.events.EventTarget implements flash.events.IEventDispatcher, goog.events.ListenableKey {public function A() {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @implements {flash.events.IEventDispatcher}\n * @implements {goog.events.ListenableKey}\n */\norg.apache.flex.A = function() {\n  org.apache.flex.A.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Override
    @Test
    public void testExtendsConstructor_super()
    {
        IClassNode node = getClassNode("public class A extends goog.events.EventTarget { public function A() { super('foo', 42);}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n */\norg.apache.flex.A = function() {\n  org.apache.flex.A.base(this, 'constructor', 'foo', 42);\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Test
    public void testConstructor_withArgumentNameMatchingMemberName()
    {
        IClassNode node = getClassNode("public class B {public function B(arg1:String) {this.arg1 = arg1}; public var arg1:String;}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n * @param {string} arg1\n */\norg.apache.flex.B = function(arg1) {\n  this.arg1 = arg1;\n};\n\n\n/**\n * @export\n * @type {string}\n */\norg.apache.flex.B.prototype.arg1;";
        assertOut(expected);
    }

    @Test
    public void testMethod_withImplicitSelfInReturnValue()
    {
        IClassNode node = getClassNode("public class B {public function B() {}; public var event:Event = new Event(); public function foo():String {return event.type;};}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n */\norg.apache.flex.B = function() {\n\nthis.event = new goog.events.Event();\n};\n\n\n/**\n * @export\n * @type {goog.events.Event}\n */\norg.apache.flex.B.prototype.event;\n\n\n/**\n * @export\n * @return {string}\n */\norg.apache.flex.B.prototype.foo = function() {\n  return this.event.type;\n};";
        assertOut(expected);
    }

    @Test
    public void testMethod_noArgsNoReturn()
    {
        IClassNode node = getClassNode("public class B {public function B() {}; public function foo():void {};}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\n/**\n * @export\n */\norg.apache.flex.B.prototype.foo = function() {\n};";
        assertOut(expected);
    }

    @Test
    public void testMethod_override()
    {
        IClassNode node = getClassNode("public class B {public function B() {}; override public function foo():void {};}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\n/**\n * @export\n * @override\n */\norg.apache.flex.B.prototype.foo = function() {\n};";
        assertOut(expected);
    }

    @Test
    public void testMethod_overrideWithFunctionBody()
    {
        IClassNode node = getClassNode("public class B {public function B() {}; override public function foo(value:Object):void {baz = ''};}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\n/**\n * @export\n * @override\n */\norg.apache.flex.B.prototype.foo = function(value) {\n  baz = '';\n};";
        assertOut(expected);
    }

    @Test
    public void testMethod_overrideSuperCall()
    {
        IClassNode node = getClassNode("public class B {public function B() {}; override public function foo():void {super.foo();};}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\n/**\n * @export\n * @override\n */\norg.apache.flex.B.prototype.foo = function() {\n  org.apache.flex.B.base(this, 'foo');\n};";
        assertOut(expected);
    }

    @Test
    public void testMethod_setterCall()
    {
        IClassNode node = getClassNode("public class B {public function B() {}; public function set baz(value:Object):void {}; public function set foo(value:Object):void {baz = value;};}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\nObject.defineProperties(org.apache.flex.B.prototype, /** @lends {org.apache.flex.B.prototype} */ {\n/** @export */\nbaz: {\nset: /** @this {org.apache.flex.B} */ function(value) {\n}},\n/** @export */\nfoo: {\nset: /** @this {org.apache.flex.B} */ function(value) {\n  this.baz = value;\n}}}\n);";
        assertOut(expected);
    }

    @Test
    public void testMethod_overrideSetterSuperCall()
    {
        IClassNode node = getClassNode("public class B extends A {public function B() {}; override public function set foo(value:Object):void {super.foo = value;};} class A {public function set foo(value:Object):void {}}");
        asBlockWalker.visitClass(node);
        String expected = "/**\n * @constructor\n * @extends {org.apache.flex.A}\n */\norg.apache.flex.B = function() {\n  org.apache.flex.B.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.B, org.apache.flex.A);\n\n\nObject.defineProperties(org.apache.flex.B.prototype, /** @lends {org.apache.flex.B.prototype} */ {\n/** @export */\nfoo: {\nset: /** @this {org.apache.flex.B} */ function(value) {\n  org.apache.flex.utils.Language.superSetter(org.apache.flex.B, this, 'foo', value);\n}}}\n);";
        assertOut(expected);
    }

    @Override
    @Test
    public void testExtendsConstructor_withArguments()
    {
        IClassNode node = getClassNode("public class A extends goog.events.EventTarget {public function A(arg1:String, arg2:int) {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @param {string} arg1\n * @param {number} arg2\n */\norg.apache.flex.A = function(arg1, arg2) {\n  org.apache.flex.A.base(this, 'constructor');\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);");
    }

    @Override
    @Test
    public void testFields()
    {
        IClassNode node = getClassNode("public class A {public var a:Object;protected var b:String; "
                + "private var c:int; internal var d:uint; var e:Number}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};\n\n\n/**\n * @export\n * @type {Object}\n */\norg.apache.flex.A.prototype.a;\n\n\n/**\n * @protected\n * @type {string}\n */\norg.apache.flex.A.prototype.b;\n\n\n/**\n * @private\n * @type {number}\n */\norg.apache.flex.A.prototype.c;\n\n\n/**\n * @export\n * @type {number}\n */\norg.apache.flex.A.prototype.d;\n\n\n/**\n * @export\n * @type {number}\n */\norg.apache.flex.A.prototype.e;");
    }

    @Override
    @Test
    public void testConstants()
    {
        IClassNode node = getClassNode("public class A {"
                + "public static const A:int = 42;"
                + "protected static const B:Number = 42;"
                + "private static const C:Number = 42;"
                + "foo_bar static const C:String = 'me' + 'you';");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};\n\n\n/**\n * @export\n * @const\n * @type {number}\n */\norg.apache.flex.A.A = 42;\n\n\n/**\n * @protected\n * @const\n * @type {number}\n */\norg.apache.flex.A.B = 42;\n\n\n/**\n * @private\n * @const\n * @type {number}\n */\norg.apache.flex.A.C = 42;\n\n\n/**\n * @export\n * @const\n * @type {string}\n */\norg.apache.flex.A.C = 'me' + 'you';");
    }

    @Override
    @Test
    public void testAccessors()
    {
        IClassNode node = getClassNode("public class A {"
                + "public function get foo1():Object{return null;}"
                + "public function set foo1(value:Object):void{}"
                + "protected function get foo2():Object{return null;}"
                + "protected function set foo2(value:Object):void{}"
                + "private function get foo3():Object{return null;}"
                + "private function set foo3(value:Object):void{}"
                + "internal function get foo5():Object{return null;}"
                + "internal function set foo5(value:Object):void{}"
                + "foo_bar function get foo6():Object{return null;}"
                + "foo_bar function set foo6(value:Object):void{}" + "}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};\n\n\nObject.defineProperties(org.apache.flex.A.prototype, /** @lends {org.apache.flex.A.prototype} */ {\n/** @export */\nfoo1: {\nget: /** @this {org.apache.flex.A} */ function() {\n  return null;\n},\nset: /** @this {org.apache.flex.A} */ function(value) {\n}},\n/** @export */\nfoo2: {\nget: /** @this {org.apache.flex.A} */ function() {\n  return null;\n},\nset: /** @this {org.apache.flex.A} */ function(value) {\n}},\n/** @export */\nfoo3: {\nget: /** @this {org.apache.flex.A} */ function() {\n  return null;\n},\nset: /** @this {org.apache.flex.A} */ function(value) {\n}},\n/** @export */\nfoo5: {\nget: /** @this {org.apache.flex.A} */ function() {\n  return null;\n},\nset: /** @this {org.apache.flex.A} */ function(value) {\n}},\n/** @export */\nfoo6: {\nget: /** @this {org.apache.flex.A} */ function() {\n  return null;\n},\nset: /** @this {org.apache.flex.A} */ function(value) {\n}}}\n);");
    }

    @Override
    @Test
    public void testMethods()
    {
        IClassNode node = getClassNode("public class A {"
                + "public function foo1():Object{return null;}"
                + "public final function foo1a():Object{return null;}"
                + "override public function foo1b():Object{return super.foo1b();}"
                + "protected function foo2(value:Object):void{}"
                + "private function foo3(value:Object):void{}"
                + "internal function foo5(value:Object):void{}"
                + "foo_bar function foo6(value:Object):void{}"
                + "public static function foo7(value:Object):void{}"
                + "foo_bar static function foo7(value:Object):void{}" + "}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};\n\n\n/**\n * @export\n * @return {Object}\n */\norg.apache.flex.A.prototype.foo1 = function() {\n  return null;\n};\n\n\n/**\n * @export\n * @return {Object}\n */\norg.apache.flex.A.prototype.foo1a = function() {\n  return null;\n};\n\n\n/**\n * @export\n * @override\n */\norg.apache.flex.A.prototype.foo1b = function() {\n  return org.apache.flex.A.base(this, 'foo1b');\n};\n\n\n/**\n * @protected\n * @param {Object} value\n */\norg.apache.flex.A.prototype.foo2 = function(value) {\n};\n\n\n/**\n * @private\n * @param {Object} value\n */\norg.apache.flex.A.prototype.foo3 = function(value) {\n};\n\n\n/**\n * @param {Object} value\n */\norg.apache.flex.A.prototype.foo5 = function(value) {\n};\n\n\n/**\n * @param {Object} value\n */\norg.apache.flex.A.prototype.foo6 = function(value) {\n};\n\n\n/**\n * @export\n * @param {Object} value\n */\norg.apache.flex.A.foo7 = function(value) {\n};\n\n\n/**\n * @param {Object} value\n */\norg.apache.flex.A.foo7 = function(value) {\n};");
    }

    @Test
    public void testMethodsWithLocalFunctions()
    {
        IClassNode node = getClassNode("public class B {"
                + "public function foo1():Object{function bar1():Object {return null;}; return bar1()}"
                + "public function foo2():Object{function bar2(param1:Object):Object {return null;}; return bar2('foo');}"
                + "}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\n/**\n * @export\n * @return {Object}\n */\norg.apache.flex.B.prototype.foo1 = function() {\n  var self = this;\n  function bar1() {\n    return null;\n  };\n  return bar1();\n};\n\n\n/**\n * @export\n * @return {Object}\n */\norg.apache.flex.B.prototype.foo2 = function() {\n  var self = this;\n  function bar2(param1) {\n    return null;\n  };\n  return bar2('foo');\n};");
    }

    @Test
    public void testMethodsWithLocalFunctions2()
    {
        IClassNode node = getClassNode("public class B {"
                + "public var baz1:String;"
                + "public function foo1():String{function bar1():String {return baz1;}; return bar1()}"
                + "public function foo2():String{function bar2(param1:String):String {return param1 + baz1;}; return bar2('foo');}"
                + "}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\n/**\n * @export\n * @type {string}\n */\norg.apache.flex.B.prototype.baz1;\n\n\n/**\n * @export\n * @return {string}\n */\norg.apache.flex.B.prototype.foo1 = function() {\n  var self = this;\n  function bar1() {\n    return this.baz1;\n  };\n  return bar1();\n};\n\n\n/**\n * @export\n * @return {string}\n */\norg.apache.flex.B.prototype.foo2 = function() {\n  var self = this;\n  function bar2(param1) {\n    return param1 + this.baz1;\n  };\n  return bar2('foo');\n};");
    }

    @Test
    public void testClassWithoutConstructor()
    {
        /* AJH couldn't find a way to reproduce the code paths
         * in a simple test case.  May require multiple compilation
         * units in the same package.
         */
        
        // (erikdebruin) what's wrong with this test case and/or the resulting code?
        
        // (erikdebruin) if you're looking for a way to test multiple cu's 
        //               (a project), look in 'TestGoogProject' for an example
        
        IClassNode node = getClassNode("public class B {"
                + "public function clone():B { return new B() }"
                + "}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.B = function() {\n};\n\n\n/**\n * @export\n * @return {org.apache.flex.B}\n */\norg.apache.flex.B.prototype.clone = function() {\n  return new org.apache.flex.B();\n};");
    }

    @Override
    @Test
    public void testSimple()
    {
        IClassNode node = getClassNode("public class A{}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};");
    }

    @Override
    @Test
    public void testSimpleInternal()
    {
        // (erikdebruin) the AS compiler will enforce 'internal' namespace, 
        //               in JS we ignore it
        IClassNode node = getClassNode("internal class A{}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};");
    }

    @Override
    @Test
    public void testSimpleFinal()
    {
        // (erikdebruin) the AS compiler will enforce the 'final' keyword, 
        //               in JS we ignore it
        IClassNode node = getClassNode("public final class A{}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};");
    }

    @Override
    @Test
    public void testSimpleDynamic()
    {
        // (erikdebruin) all JS objects are 'dynamic' by design
        IClassNode node = getClassNode("public dynamic class A{}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};");
    }

    @Override
    @Test
    public void testSimpleImplements()
    {
        IClassNode node = getClassNode("public class A implements IEventDispatcher {public function A() {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @implements {flash.events.IEventDispatcher}\n */\norg.apache.flex.A = function() {\n};");
    }

    @Override
    @Test
    public void testSimpleImplementsMultiple()
    {
        IClassNode node = getClassNode("public class A implements IEventDispatcher, ListenableKey {public function A() {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @implements {flash.events.IEventDispatcher}\n * @implements {goog.events.ListenableKey}\n */\norg.apache.flex.A = function() {\n};");
    }


    @Override
    @Test
    public void testConstructor()
    {
        IClassNode node = getClassNode("public class A {public function A() { }}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n */\norg.apache.flex.A = function() {\n};");
    }


    @Override
    @Test
    public void testConstructor_withArguments()
    {
        IClassNode node = getClassNode("public class A {public function A(arg1:String, arg2:int) {}}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @param {string} arg1\n * @param {number} arg2\n */\norg.apache.flex.A = function(arg1, arg2) {\n};");
    }

    @Test
    public void testConstructor_withBodyAndComplexInitializer()
    {
        IClassNode node = getClassNode("public class A {public function A(arg1:String, arg2:int) {arg2 = arg2 + 2;} public var foo:Array = [];}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @param {string} arg1\n * @param {number} arg2\n */\norg.apache.flex.A = function(arg1, arg2) {\n  \n  this.foo = [];\n  arg2 = arg2 + 2;\n};\n\n\n/**\n * @export\n * @type {Array}\n */\norg.apache.flex.A.prototype.foo;");
    }

    @Test
    public void testConstructor_withImplicitSuperAndBodyAndComplexInitializer()
    {
        IClassNode node = getClassNode("public class A extends EventTarget {public function A(arg1:String, arg2:int) {arg2 = arg2 + 2;} public var foo:Array = [];}");
        asBlockWalker.visitClass(node);
        assertOut("/**\n * @constructor\n * @extends {goog.events.EventTarget}\n * @param {string} arg1\n * @param {number} arg2\n */\norg.apache.flex.A = function(arg1, arg2) {\n  org.apache.flex.A.base(this, 'constructor');\n  \n  this.foo = [];\n  arg2 = arg2 + 2;\n};\ngoog.inherits(org.apache.flex.A, goog.events.EventTarget);\n\n\n/**\n * @export\n * @type {Array}\n */\norg.apache.flex.A.prototype.foo;");
    }

    protected IBackend createBackend()
    {
        return new FlexJSBackend();
    }

}
