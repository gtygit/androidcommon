/*
 * Copyright (C) 2015 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.webkitserver.utilities;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UrlUtilsTest {

  @Test
  public void testNoHashOrParameters() {
    String fileName = "this/test/file/path/.html";
    this.assertRetrieveFileNameHelper(fileName, fileName);
  }

  @Test
  public void testEmptyString() {
    this.assertRetrieveFileNameHelper("", ""); 
  }

  @Test
  public void testOnlyHash() {
    String segment = "test/file#foo";
    this.assertRetrieveFileNameHelper("test/file", segment);
  }

  @Test
  public void testOnlyQueryParams() {
    String segment = "pretty/little/liar?foo&bar=3";
    this.assertRetrieveFileNameHelper("pretty/little/liar", segment);
  }

  @Test
  public void testHashAndQueryParams() {
    String segment = "test/test/test.html#foo?bar=3&baz=55";
    this.assertRetrieveFileNameHelper("test/test/test.html", segment);
  }

  @Test
  public void testGetIndexOfParamsNoParams() {
    String segment = "test/test/test.html";
    this.assertGetIndexHelper(segment, -1);
  }

  @Test
  public void testGetIndexOfParamsHash() {
    String segment = "test/test.html#foo";
    int expected = 14;
    this.assertGetIndexHelper(segment, expected);
  }

  @Test
  public void testGetIndexOfQueryHash() {
    String segment = "this/is/a/file/that/i/like.html?foo=bar";
    int expected = 31;
    this.assertGetIndexHelper(segment, expected);
  }

  @Test
  public void testGetIndexOfBoth() {
    String segment = "foo/bar.html#foo?bar=baz";
    int expected = 12;
    this.assertGetIndexHelper(segment, expected);
  }

  @Test
  public void testGetParamsNone() {
    String segment = "this/test/file/path/.html";
    this.assertGetParamsHelper(segment, "");
  }

  @Test
  public void testGetParamsHash() {
    String segment = "test/file#foo";
    this.assertGetParamsHelper(segment, "#foo");
  }

  @Test
  public void testGetParamsQuery() {
    String segment = "pretty/little/liar?foo&bar=3";
    this.assertGetParamsHelper(segment, "?foo&bar=3");
  }

  @Test
  public void testGetParamsBoth() {
    String segment = "test/test/test.html#foo?bar=3&baz=55";
    this.assertGetParamsHelper(segment, "#foo?bar=3&baz=55");
  }
  
  /**
   * Take start, retrieve the file name, and assert that the result is equal to
   * expected.
   * @param expected
   * @param start
   */
  protected void assertRetrieveFileNameHelper(String expected, String start) {
    String result = UrlUtils.getFileNameFromUriSegment(start);
    assertEquals(expected, result);
  }
  
  protected void assertGetIndexHelper(String segment, int expected) {
    int actual = UrlUtils.getIndexOfParameters(segment);
    assertEquals(expected, actual);
  }
  
  protected void assertGetParamsHelper(String segment, String expected) {
    String actual = UrlUtils.getParametersFromSegment(segment);
    assertEquals(expected, actual);
  }

}
