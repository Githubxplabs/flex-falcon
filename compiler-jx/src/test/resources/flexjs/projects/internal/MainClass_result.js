/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * MainClass
 *
 * @fileoverview
 *
 * @suppress {checkTypes|accessControls}
 */

goog.provide('MainClass');

goog.require('OtherClass');



/**
 * @constructor
 */
MainClass = function() {
};


/**
 * Metadata
 *
 * @type {Object.<string, Array.<Object>>}
 */
MainClass.prototype.FLEXJS_CLASS_INFO = { names: [{ name: 'MainClass', qName: 'MainClass'}] };


/**
 * Prevent renaming of class. Needed for reflection.
 */
goog.exportSymbol('MainClass', MainClass);



/**
 * Reflection
 *
 * @return {Object.<string, Function>}
 */
MainClass.prototype.FLEXJS_REFLECTION_INFO = function () {
  return {
    variables: function () {
      return {
      };
    },
    accessors: function () {
      return {
      };
    },
    methods: function () {
      return {
        'MainClass': { type: '', declaredBy: 'MainClass'}
      };
    }
  };
};



/**
 * @constructor
 */
MainClass.InternalClass = function() {
  this.foo = new OtherClass();
};


/**
 * @export
 * @type {OtherClass}
 */
MainClass.InternalClass.prototype.foo;


/**
 * Metadata
 *
 * @type {Object.<string, Array.<Object>>}
 */
MainClass.InternalClass.prototype.FLEXJS_CLASS_INFO = { names: [{ name: 'InternalClass', qName: 'MainClass.InternalClass'}] };


/**
 * Prevent renaming of class. Needed for reflection.
 */
goog.exportSymbol('MainClass.InternalClass', MainClass.InternalClass);



/**
 * Reflection
 *
 * @return {Object.<string, Function>}
 */
MainClass.InternalClass.prototype.FLEXJS_REFLECTION_INFO = function () {
  return {
    variables: function () {
      return {
        'foo': { type: 'OtherClass'}
      };
    },
    accessors: function () {
      return {
      };
    },
    methods: function () {
      return {
        'InternalClass': { type: '', declaredBy: 'MainClass.InternalClass'}
      };
    }
  };
};
