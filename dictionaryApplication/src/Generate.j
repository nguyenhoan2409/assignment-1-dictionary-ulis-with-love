/** +---------------------------------------+
 *  | Project: Dictionary - Assignment 1	|
 *  | Class: Generate						|
 *  | Group: ULIS with love					|
 *  +---------------------------------------+
 */

// Generate locale file

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;

public class Generate {
	private static Properties prop = new Properties();

	public static void main (String[] args) {
		String lang = (args[0].equals("vi") ? "vi_VN" : "en_US");
		OutputStream out = null;

		try {
			out = new FileOutputStream("locale_" + lang + ".properties");

			// 1. title

			prop.setProperty("dictionary.title", (lang.equals("vi_VN") ? "Từ điển Anh - Việt" : "Advanced Dictionary"));

			// 2. button

			prop.setProperty("dictionary.ok", (lang.equals("vi_VN") ? "Đồng ý" : "OK"));
			prop.setProperty("dictionary.cancel", (lang.equals("vi_VN") ? "Huỷ bỏ" : "Cancel"));

			// 3. left box

			prop.setProperty("dictionary.search", (lang.equals("vi_VN") ? "Tìm" : "Search"));
			prop.setProperty("dictionary.all", (lang.equals("vi_VN") ? "Tất cả" : "All"));
			prop.setProperty("dictionary.recent", (lang.equals("vi_VN") ? "Gần đây" : "Recent"));
			prop.setProperty("dictionary.entries", (lang.equals("vi_VN") ? "Danh sách mục từ" : "Dictionary Entries"));

			// 4. menu

			// 4.1. dictionaries menu

			prop.setProperty("dictionary.dictionaries", (lang.equals("vi_VN") ? "Từ điển" : "Dictionaries"));

			// 4.1.1. txt dict.

			prop.setProperty("dictionary.dictionary.txt", (lang.equals("vi_VN") ? "Từ điển Text" : "Text Dictionary"));
			prop.setProperty("dictionary.dictionary.txt.title", (lang.equals("vi_VN") ? "Nạp từ tệp" : "Load from Text"));
			prop.setProperty("dictionary.dictionary.txt.txtfile", (lang.equals("vi_VN") ? "Tên tệp tin" : "File name"));

			// 4.1.2. db dict

			prop.setProperty("dictionary.dictionary.mysql", (lang.equals("vi_VN") ? "Từ điển MySQL" : "MySQL Dictionary"));
			prop.setProperty("dictionary.dictionary.mysql.title", (lang.equals("vi_VN") ? "Nạp từ MySQL" : "Load from MySQL"));
			prop.setProperty("dictionary.dictionary.mysql.host", (lang.equals("vi_VN") ? "Máy chủ" : "Host"));
			prop.setProperty("dictionary.dictionary.mysql.port", (lang.equals("vi_VN") ? "Cổng" : "Port"));
			prop.setProperty("dictionary.dictionary.mysql.user", (lang.equals("vi_VN") ? "Tài khoản" : "Username"));
			prop.setProperty("dictionary.dictionary.mysql.pass", (lang.equals("vi_VN") ? "Mật khẩu" : "Password"));
			prop.setProperty("dictionary.dictionary.mysql.db", (lang.equals("vi_VN") ? "Cơ sở dữ liệu" : "Database"));
			prop.setProperty("dictionary.dictionary.mysql.tbl", (lang.equals("vi_VN") ? "Bảng" : "Table"));

			// 4.2. tools menu

			prop.setProperty("dictionary.tool", (lang.equals("vi_VN") ? "Công cụ" : "Tools"));

			// 4.2.1. dict. mgmt

			prop.setProperty("dictionary.tool.mgmt", (lang.equals("vi_VN") ? "Quản lí" : "Dict. Mgmt"));
			prop.setProperty("dictionary.tool.mgmt.word", (lang.equals("vi_VN") ? "Từ" : "Word"));
			prop.setProperty("dictionary.tool.mgmt.meaning", (lang.equals("vi_VN") ? "Nghĩa" : "Meaning"));
			prop.setProperty("dictionary.tool.mgmt.newmeaning", (lang.equals("vi_VN") ? "Nghĩa mới" : "New meaning"));
			prop.setProperty("dictionary.tool.mgmt.filename", (lang.equals("vi_VN") ? "Tên tệp tin" : "File name"));

			prop.setProperty("dictionary.tool.mgmt.add", (lang.equals("vi_VN") ? "Thêm từ mới" : "Add new word"));
			prop.setProperty("dictionary.tool.mgmt.edit", (lang.equals("vi_VN") ? "Chỉnh sửa từ" : "Edit word"));
			prop.setProperty("dictionary.tool.mgmt.remove", (lang.equals("vi_VN") ? "Xoá từ" : "Remove word"));
			prop.setProperty("dictionary.tool.mgmt.export", (lang.equals("vi_VN") ? "Xuất từ điển" : "Export to file"));

			// 4.2.2. listening

			prop.setProperty("dictionary.tool.listening", (lang.equals("vi_VN") ? "Nghe" : "Listening"));
			prop.setProperty("dictionary.tool.listening.text", (lang.equals("vi_VN") ? "Nhập văn bản" : "Input the text"));
			prop.setProperty("dictionary.tool.listening.listen", (lang.equals("vi_VN") ? "Nghe" : "Listen"));

			// 4.2.3. google

			prop.setProperty("dictionary.tool.google", (lang.equals("vi_VN") ? "Google Dịch" : "Google API"));
			prop.setProperty("dictionary.tool.google.text", (lang.equals("vi_VN") ? "Nhập văn bản" : "Input the text"));
			prop.setProperty("dictionary.tool.google.translation", (lang.equals("vi_VN") ? "Giải nghĩa" : "Translation"));
			prop.setProperty("dictionary.tool.google.translate", (lang.equals("vi_VN") ? "Dịch" : "Translate"));

			// 4.2.4. options

			prop.setProperty("dictionary.tool.option", (lang.equals("vi_VN") ? "Cấu hình" : "Preferences"));
			prop.setProperty("dictionary.tool.option.voice", (lang.equals("vi_VN") ? "Giọng nói" : "Voice"));
			prop.setProperty("dictionary.tool.option.voice.male", (lang.equals("vi_VN") ? "Anh - Mỹ (nam)" : "en-US (male)"));
			prop.setProperty("dictionary.tool.option.voice.female", (lang.equals("vi_VN") ? "Anh - Mỹ (nữ)" : "en-US (female)"));
			prop.setProperty("dictionary.tool.option.defaultLocale", (lang.equals("vi_VN") ? "Ngôn ngữ" : "Language"));
			prop.setProperty("dictionary.tool.option.defaultLocale.en", (lang.equals("vi_VN") ? "Tiếng Anh" : "English"));
			prop.setProperty("dictionary.tool.option.defaultLocale.vi", (lang.equals("vi_VN") ? "Tiếng Việt" : "Vietnamese"));

			// 4.3. help menu

			prop.setProperty("dictionary.help", (lang.equals("vi_VN") ? "Trợ giúp" : "Help"));

			// 4.3.1. home

			prop.setProperty("dictionary.help.home", (lang.equals("vi_VN") ? "Trang chủ" : "Home"));

			// 4.3.2. about

			prop.setProperty("dictionary.help.about", (lang.equals("vi_VN") ? "Thông tin" : "About"));
			prop.setProperty("dictionary.help.about.content", (lang.equals("vi_VN") ?
				"Từ điển Anh - Việt: bài tập lớn số 1\n" +
				"Nhóm: ULIS with Love\n" +
				"Thành viên:\n" +
				"\tPhạm Thanh Tùng\n" +
				"\tNguyễn Văn Linh\n" +
				"Giao diện dựa trên phần mềm 'Từ điển Lạc Việt' - LAC VIET Computing Corp.\n" +
				"Các thư viện đi kèm:\n" +
				"\tFreeTTS + MBrola (phát âm)\n" +
				"\tConnectorJ (kết nối MySQL)\n" +
				"\tJava JSON (kết nối Google Dịch)\n" +
				"Từ điển có sẵn:\n" +
				"\tTừ điển Anh - Việt (FVDP - Hồ Ngọc Đức) - phiên bản text\n" +
				"\tTừ điển Anh - Việt (eDict - BKAV) - phiên bản MySQL (nguồn chưa xác thực)"
				:
				"Advanced Dictionary: Assignment 1\n" +
				"Group: ULIS with Love\n" +
				"Members:\n" +
				"\tPham Thanh Tung\n" +
				"\tNguyen Van Linh\n" +
				"Interface: based on 'Lac Viet dictionary' - LAC VIET Computing Corp.\n" + 
				"Included libs:\n" +
				"\tFreeTTS + MBrola (voice)\n" +
				"\tConnectorJ (MySQL Connector)\n" +
				"\tJava JSON (Google API Connector)\n" +
				"Available dictionaries:\n" +
				"\tEnglish - Vietnamese dictionary (FVDP - Ho Ngoc Duc) - text version\n" +
				"\tEnglish - Vietnamese dictionary (eDict - BKAV) - MySQL version (source is not verified)"
			));

			prop.setProperty("dictionary.help.help", (lang.equals("vi_VN") ? "Trợ giúp" : "Help"));
			prop.setProperty("dictionary.help.help.content", (lang.equals("vi_VN") ?
			"Chạy chương trình với tham số 'clean' để khôi phục cài đặt gốc\nThư mục chứa từ điển: res/dictionaries/\nThư mục chứa từ điển đã xuất: res/export/\nMuốn nhập dữ liệu text, phải chép tệp vào thư mục 'res/dictionaries/'"
			:
			"Run the program with argument 'clean' to reset all properties\nDictionaries directory: res/dictionaries/\nExported dictionaries directory: res/export/\nIf you want to import txt, please copy it to this directory first 'res/dictionaries/'"));

			// 5. langSwitch

			prop.setProperty("dictionary.langswitch", (lang.equals("vi_VN") ? "English" : "Tiếng Việt"));

			prop.store(out, null);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (out != null)
				try {
					out.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}