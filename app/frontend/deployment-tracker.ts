import Type from './generated/org/vaadin/artur/hillamicro/shared/MessagingConfiguration/Type';
import { appStore } from './stores/app-store';
import { DeploymentInfoEndpoint } from './generated/endpoints';

DeploymentInfoEndpoint.getDeploymentUpdates().onNext((e) => {
    if (e.type == Type.DEPLOY) {
      appStore.federationRoutes = [
        ...appStore.federationRoutes,
        {
          tag: e.applicationInfo.tag,
          icon: e.applicationInfo.icon,
          title: e.applicationInfo.title,
          importPath: e.applicationInfo.importPath,
          path: e.applicationInfo.path,
        },
      ];
    } else if (e.type == Type.UNDEPLOY) {
      appStore.federationRoutes = appStore.federationRoutes.filter((route) => route.tag != e.applicationInfo.tag);
    }
    console.log(`App ${e.applicationInfo.title} was ${e.type === Type.DEPLOY ? 'deployed' : 'undeployed'}`);
  });
  