/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
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

package com.google.errorprone.matchers;

import com.google.errorprone.VisitorState;
import com.sun.source.tree.Tree;

/**
 * Whether the given TreeHolder contains a match for the Tree at the time the matcher is run.
 * This can be used with a capture() to check against the result of a previous match.
 * @author alexeagle@google.com (Alex Eagle)
 */
public class Same<T extends Tree> implements Matcher<T> {
  private final TreeHolder tree;

  public Same(TreeHolder tree) {
    this.tree = tree;
  }

  @Override
  public boolean matches(T tree, VisitorState state) {
    return this.tree.get().equals(tree);
  }
}