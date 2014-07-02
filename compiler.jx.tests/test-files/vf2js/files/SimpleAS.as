////////////////////////////////////////////////////////////////////////////////
//
//  Licensed to the Apache Software Foundation (ASF) under one or more
//  contributor license agreements.  See the NOTICE file distributed with
//  this work for additional information regarding copyright ownership.
//  The ASF licenses this file to You under the Apache License, Version 2.0
//  (the "License"); you may not use this file except in compliance with
//  the License.  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
////////////////////////////////////////////////////////////////////////////////
package org.apache.flex
{

import flash.events.IEventDispatcher;

import spark.components.Button;

public dynamic class A extends spark.components.Button implements IEventDispatcher
{
	public static const MY_CLASS_CONST:String = "myClassConst";
	
	public function A()
	{
		trace(typeof "a");
	}
	
	private var _a:ArgumentError = new ArgumentError();

	public const MY_INSTANCE_CONST:String = "myInstanceConst";
}
}