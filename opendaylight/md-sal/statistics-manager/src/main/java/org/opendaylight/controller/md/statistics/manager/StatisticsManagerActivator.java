/*
 * Copyright IBM Corporation, 2013.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.md.statistics.manager;

import org.opendaylight.controller.sal.binding.api.AbstractBindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.data.DataBrokerService;
import org.opendaylight.controller.sal.binding.api.data.DataProviderService;
import org.osgi.framework.BundleContext;

public class StatisticsManagerActivator extends AbstractBindingAwareProvider {

    private static ProviderContext pSession;
    
    private static StatisticsProvider statsProvider = new StatisticsProvider();
   
    @Override
    public void onSessionInitiated(ProviderContext session) {
        
        pSession = session;
        DataProviderService dps = session.<DataProviderService>getSALService(DataProviderService.class);
        StatisticsManagerActivator.statsProvider.setDataService(dps);
        DataBrokerService dbs = session.<DataBrokerService>getSALService(DataBrokerService.class);
        StatisticsManagerActivator.statsProvider.setDataBrokerService(dbs);
        NotificationProviderService nps = session.<NotificationProviderService>getSALService(NotificationProviderService.class);
        StatisticsManagerActivator.statsProvider.setNotificationService(nps);
        StatisticsManagerActivator.statsProvider.start();

    }
    
    @Override
    protected void stopImpl(BundleContext context) {
        StatisticsManagerActivator.statsProvider.close();
    }
    
    public static ProviderContext getProviderContext(){
        return pSession;
    }

}
