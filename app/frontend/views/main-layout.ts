import { html } from 'lit';
import { customElement } from 'lit/decorators.js';
import { appStore } from '../stores/app-store';
import { Layout } from './view';

//@ts-ignore
import('components/all.js');

interface RouteInfo {
  path: string;
  title: string;
  icon: string;
}

@customElement('main-layout')
export class MainLayout extends Layout {
  render() {
    return html`
      <vaadin-app-layout primary-section="drawer">
        <header slot="drawer">
          <h1 class="text-l m-0">${appStore.applicationName}</h1>
        </header>
        <vaadin-scroller slot="drawer" scroll-direction="vertical">
          <!-- vcf-nav is not yet an official component -->
          <!-- For documentation, visit https://github.com/vaadin/vcf-nav#readme -->
          <vcf-nav aria-label="${appStore.applicationName}">
            ${this.getMenuRoutes().map(
              (viewRoute) => html`
                <vcf-nav-item path=${viewRoute.path}>
                  <span class="${viewRoute.icon} nav-item-icon" slot="prefix" aria-hidden="true"></span>
                  ${viewRoute.title}
                </vcf-nav-item>
              `
            )}
          </vcf-nav>
        </vaadin-scroller>

        <vaadin-drawer-toggle slot="navbar" aria-label="Menu toggle"></vaadin-drawer-toggle>
        <h2 slot="navbar" class="text-l m-0">${appStore.currentViewTitle}</h2>

        <slot></slot>
      </vaadin-app-layout>
    `;
  }

  connectedCallback() {
    super.connectedCallback();
    this.classList.add('block', 'h-full');
    // this.reaction(
    //   () => appStore.location,
    //   () => {
    //     AppLayout.dispatchCloseOverlayDrawerEvent();
    //   }
    // );
  }

  private getMenuRoutes(): RouteInfo[] {
    return [...(appStore.federationRoutes.filter((route) => !!route.title) as RouteInfo[])];
  }
}
