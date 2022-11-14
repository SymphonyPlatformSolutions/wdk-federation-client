package com.symphony.devsol.federation.activity;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WhoAmIExecutor implements ActivityExecutor<WhoAmI>  {
    private final SessionService session;

    @Override
    public void execute(ActivityExecutorContext<WhoAmI> context) {
        context.setOutputVariable("session", session.getSession());
    }
}
