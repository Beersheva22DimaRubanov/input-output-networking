package telran.utils;

public interface Handler {
	void publish (LoggerRecord loggerRecord);
	default void close() {};
}
