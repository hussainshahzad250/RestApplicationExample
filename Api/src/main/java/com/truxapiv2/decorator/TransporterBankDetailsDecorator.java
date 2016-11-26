/*package com.truxapiv2.decorator;

import org.displaytag.decorator.TableDecorator;

import com.truxapiv2.model.TransporterBankDetails;

public class TransporterBankDetailsDecorator extends TableDecorator {

	public TransporterBankDetailsDecorator() {
		super();
	}

	public String getEditUrl() {
		String ctx = this.getPageContext().getServletContext().getContextPath();
		TransporterBankDetails name = (TransporterBankDetails) getCurrentRowObject();
		return "<a href=\"" + ctx + "/bankDetails/edit/" + name.getId()
				+ "\">Edit</a>";

	}

	public String getDeleteUrl() {
		String ctx = this.getPageContext().getServletContext().getContextPath();
		TransporterBankDetails name= (TransporterBankDetails) getCurrentRowObject();
		return "<a href=\"" + ctx + "/bankDetails/remove/" + name.getId()
				+ "\">Delete</a>";

	}

}
*/