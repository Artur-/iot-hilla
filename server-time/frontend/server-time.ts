import { Subscription } from '@hilla/frontend';
import '@vaadin/button';
import 'j-elements';
import { html, LitElement } from 'lit';
import { state } from 'lit/decorators.js';
import { ServerTimeEndpoint } from './generated/endpoints';

export class ServerTime extends LitElement {
  @state()
  serverTime: string = '<fetching>';
  @state()
  subscribedServerTime: string = '';
  @state()
  sub?: Subscription<string>;

  async connectedCallback(): Promise<void> {
    super.connectedCallback();
    this.serverTime = await ServerTimeEndpoint.getServerTime();
  }

  render() {
    return html`
      <j-card>
        <div>Server time when opening view: ${this.serverTime}</div>
        <div>
          ${this.sub
            ? html`
                    <vaadin-button @click=${this.unsubscribe}
                      >Unsubscribe</vaadin-button
                    ></vaadin-button>
                    <span>Server time is: ${this.subscribedServerTime}</span>
                  `
            : html`
                    <vaadin-button @click=${this.subscribe}
                      >Subscribe to time updates</vaadin-button
                    ></vaadin-button>
                  `}
        </div>
      </j-card>
    `;
  }
  subscribe() {
    this.sub = ServerTimeEndpoint.subscribe().onNext((time) => (this.subscribedServerTime = time));
  }
  unsubscribe() {
    if (this.sub) {
      this.sub.cancel();
      this.sub = undefined;
    }
  }
}

customElements.define('server-time', ServerTime);
