package util.util;

import org.apache.logging.log4j.LogManager;

public class OutputLogger {
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class.getName());

	/**
	 * WARNレベルのログ出力を行います。
	 *
	 * @param elements
	 *            エラー原因
	 * @param occueredErrorPoint
	 *            エラー発生箇所
	 */
	private void outputWarn(StackTraceElement elements, String occueredErrorPoint) {
		String causeErrorPoint = elements.getClassName() + "." + elements.getMethodName() + "(" + elements.getFileName()
				+ ":" + elements.getLineNumber() + ")";
		logger.warn("[エラー根元]={}", causeErrorPoint);
		logger.warn("[エラー発生箇所]={}", occueredErrorPoint);
	}

	/**
	 * エラー発生箇所情報を取得します
	 * @param e
	 */
	public void generateOutputWarnLogInfo(String apiName, Exception e) {
		String occueredErrorPoint = null;
		// エラー根元をスタックトレースから取得する
		StackTraceElement[] elements = e.getStackTrace();
		// エラー発生箇所をスタックトレースから取得する
		for (StackTraceElement ste : elements) {
			if (ste.toString().contains(apiName)) {
				occueredErrorPoint = ste.toString();
				break;
			}
		}
		outputWarn(elements[0], occueredErrorPoint);
	}

	public String generateOutputInfoLogInfo(StackTraceElement throwableStackTraceElement, int outputLogLine){
		StringBuilder sb = new StringBuilder();
		String info = null;
		sb.append(throwableStackTraceElement.getClassName() + "." + throwableStackTraceElement.getMethodName() + ":");
		sb.append(throwableStackTraceElement.getLineNumber() + outputLogLine);
		info = sb.toString();
		sb = null;
		return info;
	}

}
