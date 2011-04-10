package org.zaproxy.zap.extension.httppanel.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.view.HttpPanel;
import org.zaproxy.zap.extension.httppanel.HttpPanelHexUi;
import org.zaproxy.zap.extension.httppanel.HttpPanelTableUi;
import org.zaproxy.zap.extension.httppanel.HttpPanelTextUi;
import org.zaproxy.zap.extension.search.SearchMatch;

public class HttpDataModelReqPost extends HttpDataModel {

	public HttpDataModelReqPost(HttpPanel httpPanel,
			HttpPanelHexUi httpPanelHexUi, HttpPanelTableUi httpPanelTableUi,
			HttpPanelTextUi httpPanelTextUi) {
		super(httpPanel, httpPanelHexUi, httpPanelTableUi, httpPanelTextUi);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hexDataFromView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hexDataToView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableDataFromView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableDataToView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void textDataFromView() {
		getHttpMessage().setRequestBody(httpPanelTextUi.getData());
	}

	@Override
	public void textDataToView() {
		httpPanelTextUi.setData(getHttpMessage().getRequestBody().toString());
	}

	/* for UI search
	 * should use exactly same data as textDataToView(), or else it does not show correctly
	 */
	public void search(Pattern p, List<SearchMatch> matches) {
		Matcher m;
		m = p.matcher(getHttpMessage().getRequestBody().toString());
		while (m.find()) {
			matches.add(
				new SearchMatch(SearchMatch.Locations.REQUEST_BODY,
						m.start(), m.end()));
		}
	}
	
}