package br.com.estudio89.grabber.test;

import br.com.estudio89.grabber.annotation.GrabberFactory;
import br.com.estudio89.grabber.annotation.InstantiationListener;
import java.util.ArrayList;
import java.util.List;

public class SyncManagerFactory implements GrabberFactory<SyncManager> {
  private static List<SyncManager> items;

  public List<SyncManager> listAll(InstantiationListener<SyncManager> listener) {
    if (items == null) {
      items = new ArrayList<>();
      SyncManager item0 = new TestSyncManagerOne.TestSyncManagerTwo();
      if (listener != null) {
        listener.onNewInstance(item0);
      }
      items.add(item0);
      SyncManager item1 = new TestSyncManagerOne();
      if (listener != null) {
        listener.onNewInstance(item1);
      }
      items.add(item1);
    }
    return items;
  }

  public List<SyncManager> listAll() {
    return listAll(null);
  }
}