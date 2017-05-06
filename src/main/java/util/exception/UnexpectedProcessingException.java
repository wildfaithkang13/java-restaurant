package util.exception;

public class UnexpectedProcessingException extends RuntimeException {

	public UnexpectedProcessingException(String string, Exception e) {
		// TODO 自動生成されたコンストラクター・スタブ
		//エラーに応じてメッセージコードを取得する
		super(string, e);
	}

	public UnexpectedProcessingException(String string) {
		super(string);
	}

}
