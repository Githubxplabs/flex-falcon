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
 * Super
 *
 * @fileoverview
 *
 * @suppress {checkTypes}
 */

goog.provide('Super');



/**
 * @constructor
 */
Super = function() {
};


/**
 * @private
 * @type {string}
 */
Super.prototype._text = '';


/**
 * @expose
 * @return {string}
 */
Super.prototype.get_text = function() {
  return this._text;
};


/**
 * @expose
 * @param {string} value
 */
Super.prototype.set_text = function(value) {
  if (value != this._text) {
    this._text = value;
  }
};


/**
 * @private
 * @type {number}
 */
Super.prototype._x = 5;


/**
 * @expose
 * @return {number}
 */
Super.prototype.get_x = function() {
  return this._x;
};


/**
 * Metadata
 *
 * @type {Object.<string, Array.<Object>>}
 */
Super.prototype.FLEXJS_CLASS_INFO = { names: [{ name: 'Super', qName: 'Super'}] };
