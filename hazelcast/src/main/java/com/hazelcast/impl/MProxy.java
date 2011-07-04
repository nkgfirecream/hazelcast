/* 
 * Copyright (c) 2008-2010, Hazel Ltd. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.hazelcast.impl;

import com.hazelcast.core.IMap;

import java.util.Set;

public interface MProxy extends IMap, IRemoveAwareProxy, IGetAwareProxy {
    String getLongName();

    void addGenericListener(Object listener, Object key, boolean includeValue, InstanceType instanceType);

    void removeGenericListener(Object listener, Object key);

    boolean containsEntry(Object key, Object value);

    boolean putMulti(Object key, Object value);

    boolean removeMulti(Object key, Object value);

    boolean add(Object value);

    int valueCount(Object key);

    Set allKeys();

    MapOperationsCounter getMapOperationCounter();

    void putForSync(Object key, Object value);

    void removeForSync(Object key);
}
