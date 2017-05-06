package util.util;

import org.apache.logging.log4j.LogManager;

public class Logger {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class.getName());

	/**
	 * エラーをログに出力する情報を生成します
	 *
	 * @param errorMessage
	 *            エラーメッセージ
	 * @param elements
	 *            エラー原因
	 * @param occueredErrorPoint
	 *            エラー発生箇所
	 */
	private void outputWarn(String errorMessage, StackTraceElement elements, String occueredErrorPoint) {
		String causeErrorPoint = elements.getClassName() + "." + elements.getMethodName() + "(" + elements.getFileName()
				+ ":" + elements.getLineNumber() + ")";
		logger.warn(errorMessage + " [エラー根元]={}", causeErrorPoint);
		logger.warn("[エラー根元]={}", causeErrorPoint);
		logger.warn("[エラー発生箇所]={}", occueredErrorPoint);

	}

	/**
	 * ログに出力する情報を生成します
	 * @param errorMessage
	 * @param e
	 */
	public void generateOutputWarnInfo(String errorMessage, Exception e) {
		String occueredErrorPoint = null;
		// エラー原因を特定する
		StackTraceElement[] elements = e.getStackTrace();
		// エラーの発生箇所を特定する
		for (StackTraceElement ste : elements) {
			if (ste.toString().contains("RegistrationServlet")) {
				occueredErrorPoint = ste.toString();
				break;
			}
		}
		outputWarn(errorMessage, elements[0], occueredErrorPoint);
	}

}
