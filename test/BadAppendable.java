import java.io.IOException;

/**
 * Appendable class that throws IOException on each method. Used for testing.
 */
public class BadAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("test");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("test");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("test");
  }
}
