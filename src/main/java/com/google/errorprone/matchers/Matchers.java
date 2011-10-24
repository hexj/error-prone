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
import com.sun.source.tree.*;
import com.sun.source.tree.Tree.Kind;
import com.sun.tools.javac.code.Type;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Static factory methods which make the DSL read better.
 *
 * TODO: it's probably worth the optimization to keep a single instance of each Matcher, rather than
 * create new instances each time the static method is called.
 *
 * @author alexeagle@google.com (Alex Eagle)
 */
public class Matchers {
  private Matchers() {}


  public static <T extends Tree> Matcher<T> allOf(final Matcher<? super T>... matchers) {
    return new Matcher<T>() {
      @Override public boolean matches(T t, VisitorState state) {
        for (Matcher<? super T> matcher : matchers) {
          if (!matcher.matches(t, state)) {
            return false;
          }
        }
        return true;
      }
    };
  }

  public static <T extends Tree> Matcher<T> capture(
      final TreeHolder<T> holder, final Matcher<Tree> matcher) {
    return new CapturingMatcher<T>(matcher, holder);
  }

  public static <T extends Tree> Matcher<T> kindOf(final Kind kind) {
    return new Matcher<T>() {
      @Override public boolean matches(T tree, VisitorState state) {
        return tree.getKind() == kind;
      }
    };
  }

  public static StaticMethodMatcher staticMethod(
      String packageName, String className, String methodName) {
    return new StaticMethodMatcher(packageName, className, methodName);
  }

  public static MethodInvocationMethodSelectMatcher methodSelect(
      Matcher<ExpressionTree> methodSelectMatcher) {
    return new MethodInvocationMethodSelectMatcher(methodSelectMatcher);
  }

  public static Matcher<MethodInvocationTree> argument(
      final int position, final Matcher<ExpressionTree> argumentMatcher) {
    return new MethodInvocationArgumentMatcher(position, argumentMatcher);
  }

  public static <T extends Tree> Matcher<Tree> parentNodeIs(Matcher<T> treeMatcher) {
    return new ParentNodeIs<T>(treeMatcher);
  }

  public static <T extends Tree> Matcher<T> not(Matcher<T> matcher) {
    return new Not<T>(matcher);
  }

  public static <T extends Tree> Matcher<T> isSubtypeOf(Type type) {
    return new IsSubtypeOf<T>(type);
  }

  public static <T extends Tree> StoreToBoolean<T> storeToBoolean(AtomicBoolean result, Matcher<T> matcher) {
    return new StoreToBoolean<T>(result, matcher);
  }

  public static <T extends Tree> EnclosingBlock<T> enclosingBlock(Matcher<BlockTree> matcher) {
    return new EnclosingBlock<T>(matcher);
  }

  public static LastStatement lastStatement(Matcher<StatementTree> matcher) {
    return new LastStatement(matcher);
  }

  public static <T extends Tree> Same<T> same(TreeHolder<T> treeHolder) {
    return new Same<T>(treeHolder);
  }
}