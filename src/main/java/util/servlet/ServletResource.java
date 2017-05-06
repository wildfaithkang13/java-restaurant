package util.servlet;

/**
 * アプリケーション内で利用するインターフェースの共通部品を
 * 定義します。
 */
public interface ServletResource {
	//インタフェース内でフィールドを宣言すると自動的にpublic static finalの定数となる
	String REQUEST_URL = "http://localhost:8080/java-restaurant/";

	/**
	 * エラーが発生した時にエラーページへリクエストを飛ばします
	 */
	//public abstractをつけなくても自動的にpublicかつabstractになる
	void gotoErrorPage(String errorMessage);
}
