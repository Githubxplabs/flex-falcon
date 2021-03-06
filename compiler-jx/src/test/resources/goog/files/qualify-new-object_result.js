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
goog.provide('org.apache.flex.A');

goog.require('flash.events.EventDispatcher');
goog.require('goog.events.EventTarget');

/**
 * @constructor
 * @extends {flash.events.EventDispatcher}
 */
org.apache.flex.A = function() {
	var self = this;
	org.apache.flex.A.base(this, 'constructor');
	self.init();
};
goog.inherits(org.apache.flex.A, flash.events.EventDispatcher);

/**
 * @private
 * @type {goog.events.EventTarget}
 */
org.apache.flex.A.prototype._privateVar;

org.apache.flex.A.prototype.init = function() {
	var self = this;
	var /** @type {goog.events.EventTarget} */ btn = new goog.events.EventTarget();
	self._privateVar = new goog.events.EventTarget();
	self.addEventListener("click", function() {
	});
};

org.apache.flex.A.prototype.start = function() {
	var self = this;
	var /** @type {string} */ localVar = self._privateVar.label;
	self.init();
	doIt();
};
