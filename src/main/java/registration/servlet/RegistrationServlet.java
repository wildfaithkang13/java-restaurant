package registration.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;

import registration.controller.RegistrationController;
import util.exception.UnexpectedProcessingException;
import util.servlet.ServletResource;
import util.util.Logger;
import util.util.OutputLogger;

/**
 * サーブレットがコントローラーの役割を行うが ここでは受け渡しのみを実施する。 コントローラーの役割はビジネスロジックのクラスが担当する
 */
public class RegistrationServlet extends HttpServlet implements ServletResource{
	private static final long serialVersionUID = 1L;
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class.getName());

	private final static String COMMON_URL = "CommonUrl";
	private final static String PROPERTY_FILE = "url.properties";
	private final static Properties prop = new Properties();
	private final static String API_NAME = "RegistrationServlet";

	// 初期化処理を行います
	public void init() throws ServletException{

		InputStream inStream = null;
		OutputLogger Logger = new OutputLogger();
		StackTraceElement throwableStackTraceElement = null;
		String outputInfoLogLine = null;
		try {
	        throwableStackTraceElement = new Throwable().getStackTrace()[0];
	        outputInfoLogLine = Logger.generateOutputInfoLogInfo(throwableStackTraceElement, 2);
			logger.info("[プロパティファイル読込開始] ({})", outputInfoLogLine);
			throwableStackTraceElement = null;
			inStream = RegistrationServlet.class.getClassLoader().getResourceAsStream(PROPERTY_FILE);
			prop.load(inStream);
		} catch (IOException e) {
			logger.warn("[プロパティ読込エラー],取得ファイル名={}", PROPERTY_FILE);
			Logger.generateOutputWarnLogInfo(API_NAME, e);
			gotoErrorPage("プロパティ読込エラー");
		} catch (NullPointerException e) {
			//TODO ログにipアドレスを出力するかどうか検討中
			logger.warn("[プロパティファイル未存在],取得ファイル名={}", PROPERTY_FILE);
			Logger.generateOutputWarnLogInfo(API_NAME, e);
			gotoErrorPage("プロパティファイル未存在");
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				throwableStackTraceElement = new Throwable().getStackTrace()[0];
				outputInfoLogLine = Logger.generateOutputInfoLogInfo(throwableStackTraceElement, 2);
				logger.info("[プロパティファイル読込終了] ({})", outputInfoLogLine);
			} catch (IOException e) {
				logger.warn("リソース解放失敗, 対象ファイル={}",PROPERTY_FILE);
				Logger.generateOutputWarnLogInfo(API_NAME, e);
			}
		}
	}

	/**
	 * GETはボタンやリンクを押したように時に何も情報がないもののリクエスト POSTは入力フォームといったリクエストする情報が存在するもの
	 * なのでボタンやリンクを押した場合はGETメソッドとなる(クエストリングのリクエストも含む)
	 * 会員登録ボタンを押しただけGETで受け取り、以降の利用規約同意画面や会員情報入力はPOSTが担当する
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// showPage(response);
		// String buildUrl = null;

		// パラメータを取得する
		String process_code = request.getParameter("process_name");

		// コントローラを呼び出す
		RegistrationController rc = new RegistrationController();
		rc.invoke(process_code);

		// 利用規約同意画面を表示する
		// String getRequestUrl = prop.getProperty(process_code);

		// 利用規約同意画面を表示する(JSON形式で必要なデータを送る)
		// sarver-restaurantに送りつける
		// buildUrl = REQUEST_URL + getRequestUrl;
		// response.sendRedirect(buildUrl);
	}

	/**
	 * 基底クラス(ビジネスロジック例外)意外のシステムエラーをキャッチします。
	 * DB接続失敗やユーザーの入力ミス等はビジネスロジック側で例外を処理します。 ログも基底クラスで出力するように設計すること
	 * ここでチェックする例外はプロパティファイルの値取得失敗だけ 利用規約同意と会員入力フォームのリクエストを受け付ける
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/*
	 * 表示確認用ページを作成するメソッド サーブレット完成次第削除する
	 */
//	private void showPage(HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		out.print("<html><head><title>テストページ</title></head><body>");
//		out.print("<h1>ようこそ！HelloServletへ</h1>");
//		out.print("</body></html>");
//
//	}

	@Override
	public void gotoErrorPage(String errorMessage) {
		throw new UnexpectedProcessingException(errorMessage);
	}

}
