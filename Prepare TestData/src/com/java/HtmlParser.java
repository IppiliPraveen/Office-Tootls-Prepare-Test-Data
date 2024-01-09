package com.java;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HtmlParser {
	
	

	public static void main(String[] args) {
		HtmlParser hp = new HtmlParser();
		hp.idsData("C:/Users/ippilip/Eclipse-Practice/Practice/src/com/praveen/VPD_Clinical_11113.html");
	}

	public Map idsData(String fname) {

		String htmlFilePath = fname;

		Map<String, String> ids = new HashMap<>();
		Set<String> checkboxs = new HashSet<>();
		Map<String, String> cb = new HashMap<>();

		try {

			Document document = Jsoup.parse(new File(htmlFilePath), "UTF-8");

			Elements inputElements = document.select("input");

			for (Element inputElement : inputElements) {

				if (inputElement.hasAttr("checked")) {
					String id = inputElement.attr("id").replace("_00_0", "");
					String value = inputElement.attr("value");

					String name = inputElement.attr("name");
					checkboxs.add(name.replace("_00_0", ""));
					if (!id.equals("") && value != null) {
						cb.put(id, value);
					}

				}

				else {
					String id = inputElement.attr("id").replace("_00_0", "");
					String value = inputElement.attr("value");

					if (!id.equals("") && value != null && !value.contains("_00_0")) {
						ids.put(id, value);
					}
				}

			}

			for (String checkbox : checkboxs) {
				String mulVal = "";
				for (String cbVal : cb.keySet()) {
					if (cbVal.contains(checkbox)) {
						mulVal += cb.get(cbVal).replace("_00_0", "").replace(checkbox + "_", "") + "^";
					}
				}
				if (mulVal.contains("^")) {
					mulVal = mulVal.substring(0, mulVal.lastIndexOf("^"));
				}
				ids.put(checkbox, mulVal);
			}

			Elements selectElements = document.select("select");

			for (Element selectElement : selectElements) {
				String id = selectElement.attr("id").replace("_00_0", "");

				Elements selectedOption = selectElement.select("option[selected]");

				if (selectedOption != null) {
					String selectedValue = selectedOption.attr("value");
					ids.put(id, selectedValue);
				} else {
				}
			}

			Elements textareaElements = document.select("textarea");

			for (Element textareaElement : textareaElements) {
				String id = textareaElement.attr("id").replace("_00_0", "");
				String value = textareaElement.text();
				ids.put(id, value);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return ids;
	}
}
