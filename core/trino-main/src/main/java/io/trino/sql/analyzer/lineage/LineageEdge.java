/*
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
package io.trino.sql.analyzer.lineage;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public record LineageEdge(Optional<LineageNode> from, Optional<LineageNode> to, Optional<String> expression)
{
    public LineageEdge(Optional<LineageNode> from, Optional<LineageNode> to, Optional<String> expression)
    {
        this.from = requireNonNull(from, "from is null");
        this.to = requireNonNull(to, "to is null");
        this.expression = requireNonNull(expression, "expression is null");
    }

    @Override
    public String toString() {
        return "LineageEdge{" +
                "from=" + from +
                ", to=" + to +
                ", expression=" + expression +
                '}';
    }
}
