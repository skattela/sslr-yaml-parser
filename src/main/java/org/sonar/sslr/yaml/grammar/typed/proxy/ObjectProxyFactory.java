/*
 * Sonar SSLR :: YAML Parser
 * Copyright (C) 2018-2019 Societe Generale
 * vincent.girard-reydet AT socgen DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.sslr.yaml.grammar.typed.proxy;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.sonar.sslr.yaml.grammar.JsonNode;

public class ObjectProxyFactory implements ProxyFactory {
  private final Map<String, ProxyFactory> methods = new HashMap<>();
  private final Class[] types;

  public ObjectProxyFactory(Class[] types) {
    Class[] extended = new Class[types.length + 1];
    System.arraycopy(types, 0, extended, 0, types.length);
    extended[types.length] = NodeProxy.class;
    this.types = extended;
  }

  public void addProperty(PropertyPointer pointer) {
    methods.put(pointer.getMethodName(), pointer);
  }

  @Override
  public Object makeProxyFor(JsonNode node) {
    return Proxy.newProxyInstance(getClass().getClassLoader(), types, new ObjectProxy(node, Collections.unmodifiableMap(methods)));
  }

}
