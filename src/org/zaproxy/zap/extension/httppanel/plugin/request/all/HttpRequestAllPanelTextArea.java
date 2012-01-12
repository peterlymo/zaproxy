/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.zap.extension.httppanel.plugin.request.all;

import javax.swing.text.BadLocationException;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.httppanel.view.text.HttpPanelTextArea;
import org.zaproxy.zap.extension.search.SearchMatch;

public class HttpRequestAllPanelTextArea extends HttpPanelTextArea {

	private static final long serialVersionUID = 923466158533211593L;
	
	//private static final String HTTP_REQUEST_HEADER_AND_BODY = "HTTP Request Header and Body";

	//private static final String SYNTAX_STYLE_HTTP_REQUEST_HEADER_AND_BODY = "text/http-request-header-body";
	
	private static RequestAllTokenMakerFactory tokenMakerFactory = null;

	public HttpRequestAllPanelTextArea(HttpMessage httpMessage) {
		super(httpMessage);
		
		//addSyntaxStyle(HTTP_REQUEST_HEADER_AND_BODY, SYNTAX_STYLE_HTTP_REQUEST_HEADER_AND_BODY);
	}
	
	@Override
	public SearchMatch getTextSelection() {
		HttpMessage httpMessage = getHttpMessage();
		//This only happens in the Request/Response Header
		//As we replace all \r\n with \n we must add one character
		//for each line until the line where the selection is.
		int tHeader = 0;
		String header = httpMessage.getRequestHeader().toString();
		int pos = 0;
		while ((pos = header.indexOf("\r\n", pos)) != -1) {
			pos += 2;
			++tHeader;
		}
		
		SearchMatch sm = null;
		int headerLen = header.length();
		if (getSelectionStart()+tHeader < headerLen) {
			int start = getSelectionStart();
			try {
				start += getLineOfOffset(start);
			} catch (BadLocationException e) {
				//Shouldn't happen.
			}
			
			int end = getSelectionEnd();
			try {
				end += getLineOfOffset(end);
			} catch (BadLocationException e) {
				//Shouldn't happen.
			}
			
			if (end > headerLen) {
				end = headerLen;
			}
			
			sm = new SearchMatch(
				httpMessage,
				SearchMatch.Location.REQUEST_HEAD, 
				start,
				end);
		} else {
			sm = new SearchMatch(
				httpMessage,
				SearchMatch.Location.REQUEST_BODY, 
				getSelectionStart()+tHeader - headerLen,
				getSelectionEnd()+tHeader - headerLen);
		}
		
		return sm;
	}
	
	@Override
	public void highlight(SearchMatch sm) {
		if (!(SearchMatch.Location.REQUEST_HEAD.equals(sm.getLocation()) ||
			SearchMatch.Location.REQUEST_BODY.equals(sm.getLocation()))) {
			return;
		}
		
		final boolean isBody = SearchMatch.Location.REQUEST_BODY.equals(sm.getLocation());
		
		//As we replace all \r\n with \n we must subtract one character
		//for each line until the line where the selection is.
		int t = 0;
		String header = sm.getMessage().getRequestHeader().toString();
		int pos = 0;
		while ((pos = header.indexOf("\r\n", pos)) != -1) {
			pos += 2;
			
			if (!isBody && pos > sm.getStart()) {
				break;
			}
			
			++t;
		}
		
		int start = sm.getStart()-t;
		int end = sm.getEnd()-t;
		
		if (isBody) {
			start += header.length();
			end += header.length();
		}
		
		int len = this.getText().length();
		if (start > len || end > len) {
			return;
		}
		
		highlight(start, end);
	}
	
	@Override
	protected synchronized CustomTokenMakerFactory getTokenMakerFactory() {
		if (tokenMakerFactory == null) {
			tokenMakerFactory = new RequestAllTokenMakerFactory();
		}
		return tokenMakerFactory;
	}
	
	private static class RequestAllTokenMakerFactory extends CustomTokenMakerFactory {
		
		public RequestAllTokenMakerFactory() {
			//String pkg = "";

			//putMapping(SYNTAX_STYLE_HTTP_REQUEST_HEADER_AND_BODY, pkg + "HttpRequestHeaderAndBodyTokenMaker");
		}
	}
}
