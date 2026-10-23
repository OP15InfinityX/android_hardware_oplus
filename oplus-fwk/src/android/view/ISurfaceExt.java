package android.view;

/**
 * Compatibility interface used by Oplus framework callers.
 *
 * @hide
 */
public interface ISurfaceExt {
    default int setMaxDequeuedBufferCount(int bufferCount) {
        return -1;
    }

    default int nativeSetMaxDequeuedBufferCount(long nativeObject, int bufferCount) {
        return -1;
    }
}
