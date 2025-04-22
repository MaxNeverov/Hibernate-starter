package com.mndev.listener;

import com.mndev.entity.Audit;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

public class AuditTableListener implements PreDeleteEventListener, PreInsertEventListener {
    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        if(event.getEntity() !=Audit.class) {
            Audit audit = Audit.builder()
                    .entityId(event.getId())
                    .entityContent(event.getEntity().toString())
                    .operation(Audit.Operation.DELETE)
                    .build();
            event.getSession().save(audit);
        }
        return false;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        if(event.getEntity() !=Audit.class) {
            Audit audit = Audit.builder()
                    .entityId(event.getId())
                    .entityContent(event.getEntity().toString())
                    .operation(Audit.Operation.INSERT)
                    .build();
            event.getSession().save(audit);
        }
        return false;
    }
}
