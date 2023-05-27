/* This defines all the components that are made available for all modules */
const promises = [];
promises.push(import('@vaadin/app-layout'));
promises.push(import('@vaadin/button'));
promises.push(import('@vaadin/charts'));
promises.push(import('@vaadin/notification'));
promises.push(import('@vaadin/text-field'));
promises.push(import('j-elements'));
promises.push(import('@vaadin-component-factory/vcf-nav'));
promises.push(import('@vaadin/app-layout/vaadin-drawer-toggle'));
promises.push(import('@vaadin/avatar'));
promises.push(import('@vaadin/icon'));
promises.push(import('@vaadin/menu-bar'));
promises.push(import('@vaadin/scroller'));
promises.push(import('@vaadin/tabs'));
promises.push(import('@vaadin/tabs/vaadin-tab'));
promises.push(import('@vaadin/vaadin-lumo-styles/vaadin-iconset'));
promises.push(import('@vaadin/vaadin-lumo-styles/utility.js'));
promises.push(import('room-info/room-info.js'));

Promise.all(promises).then(() => console.log('Components bundle loaded'));
