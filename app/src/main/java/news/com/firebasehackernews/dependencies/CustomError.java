package news.com.firebasehackernews.dependencies;

/**
 *  Custom Error
 */

class CustomError  extends Error {

    public CustomError(String detailMessage) {
      super(detailMessage);
    }
}
