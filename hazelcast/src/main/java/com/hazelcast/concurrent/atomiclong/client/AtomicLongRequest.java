/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
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
 */

package com.hazelcast.concurrent.atomiclong.client;

import com.hazelcast.client.ClientEngine;
import com.hazelcast.client.PartitionClientRequest;
import com.hazelcast.client.SecureRequest;
import com.hazelcast.concurrent.atomiclong.AtomicLongService;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.Portable;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.security.permission.ActionConstants;
import com.hazelcast.security.permission.AtomicLongPermission;

import java.io.IOException;
import java.security.Permission;

public abstract class AtomicLongRequest extends PartitionClientRequest implements Portable, SecureRequest {

    protected String name;
    protected long delta;

    protected AtomicLongRequest() {
    }

    protected AtomicLongRequest(String name, long delta) {
        this.name = name;
        this.delta = delta;
    }

    @Override
    protected int getPartition() {
        ClientEngine clientEngine = getClientEngine();
        Data key = serializationService.toData(name);
        return clientEngine.getPartitionService().getPartitionId(key);
    }

    @Override
    public String getServiceName() {
        return AtomicLongService.SERVICE_NAME;
    }

    @Override
    public int getFactoryId() {
        return AtomicLongPortableHook.F_ID;
    }

    @Override
    public void write(PortableWriter writer) throws IOException {
        writer.writeUTF("n", name);
        writer.writeLong("d", delta);
    }

    @Override
    public void read(PortableReader reader) throws IOException {
        name = reader.readUTF("n");
        delta = reader.readLong("d");
    }

    @Override
    public Permission getRequiredPermission() {
        return new AtomicLongPermission(name, ActionConstants.ACTION_MODIFY);
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{delta};
    }

    @Override
    public String getDistributedObjectName() {
        return name;
    }
}
