package com.butabuta.parser;

import android.database.Cursor;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.FileNotFoundException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TestDocumentProvider extends DocumentsProvider {

    private static final String TAG = "xxxxx";

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor queryRoots(String[] projection) throws FileNotFoundException {
        // Create a cursor with either the requested fields, or the default
        // projection if "projection" is null.
        /*final MatrixCursor result =
                new MatrixCursor(resolveRootProjection(projection));

        // It's possible to have multiple roots (e.g. for multiple accounts in the
        // same app) -- just add multiple cursor rows.
        // Construct one row for a root called "MyCloud".
        final MatrixCursor.RowBuilder row = result.newRow();
        row.add(DocumentsContract.Root.COLUMN_ROOT_ID, ROOT);
        row.add(DocumentsContract.Root.COLUMN_SUMMARY, getContext().getString(R.string.root_summary));

        // FLAG_SUPPORTS_CREATE means at least one directory under the root supports
        // creating documents. FLAG_SUPPORTS_RECENTS means your application's most
        // recently used documents will show up in the "Recents" category.
        // FLAG_SUPPORTS_SEARCH allows users to search all documents the application
        // shares.
        row.add(DocumentsContract.Root.COLUMN_FLAGS, DocumentsContract.Root.FLAG_SUPPORTS_CREATE |
                DocumentsContract.Root.FLAG_SUPPORTS_RECENTS |
                DocumentsContract.Root.FLAG_SUPPORTS_SEARCH);

        // COLUMN_TITLE is the root title (e.g. Gallery, Drive).
        row.add(DocumentsContract.Root.COLUMN_TITLE, getContext().getString(R.string.title));

        // This document id cannot change once it's shared.
        row.add(DocumentsContract.Root.COLUMN_DOCUMENT_ID, getDocIdForFile(mBaseDir));

        // The child MIME types are used to filter the roots and only present to the
        //  user roots that contain the desired type somewhere in their file hierarchy.
        row.add(DocumentsContract.Root.COLUMN_MIME_TYPES, getChildMimeTypes(mBaseDir));
        row.add(DocumentsContract.Root.COLUMN_AVAILABLE_BYTES, mBaseDir.getFreeSpace());
        row.add(DocumentsContract.Root.COLUMN_ICON, R.drawable.ic_launcher);*/

        return null;
    }

    @Override
    public Cursor queryDocument(String s, String[] strings) throws FileNotFoundException {
        // Create a cursor with the requested projection, or the default projection.
        /*final MatrixCursor result = new
                MatrixCursor(resolveDocumentProjection(projection));
        includeFile(result, documentId, null);*/
        return null;
    }

    @Override
    public Cursor queryChildDocuments(String s, String[] strings, String s1) throws FileNotFoundException {
        /*final MatrixCursor result = new
                MatrixCursor(resolveDocumentProjection(projection));
        final File parent = getFileForDocId(parentDocumentId);
        for (File file : parent.listFiles()) {
            // Adds the file's display name, MIME type, size, and so on.
            includeFile(result, null, file);
        }*/
        return null;
    }

    @Override
    public ParcelFileDescriptor openDocument(String s, String s1, @Nullable CancellationSignal cancellationSignal) throws FileNotFoundException {
        /*Log.v(TAG, "openDocument, mode: " + mode);
        // It's OK to do network operations in this method to download the document,
        // as long as you periodically check the CancellationSignal. If you have an
        // extremely large file to transfer from the network, a better solution may
        // be pipes or sockets (see ParcelFileDescriptor for helper methods).

        final File file = getFileForDocId(documentId);

        final boolean isWrite = (mode.indexOf('w') != -1);
        if(isWrite) {
            // Attach a close listener if the document is opened in write mode.
            try {
                Handler handler = new Handler(getContext().getMainLooper());
                return ParcelFileDescriptor.open(file, accessMode, handler,
                        new ParcelFileDescriptor.OnCloseListener() {
                            @Override
                            public void onClose(IOException e) {

                                // Update the file with the cloud server. The client is done
                                // writing.
                                Log.i(TAG, "A file with id " +
                                                documentId + " has been closed!
                                        Time to " +
                                        "update the server.");
                            }

                        });
            } catch (IOException e) {
                throw new FileNotFoundException("Failed to open document with id "
                        + documentId + " and mode " + mode);
            }
        } else {
            return ParcelFileDescriptor.open(file, accessMode);
        }*/
        return null;
    }
}
