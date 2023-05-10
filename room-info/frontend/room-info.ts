import '@vaadin/charts';
import 'j-elements';
import { html, LitElement } from 'lit';
import { customElement, property, state } from 'lit/decorators.js';
import SensorInfo from './generated/org/vaadin/artur/hillamicro/roominfo/SensorInfo';
import { SensorEndpoint } from './generated/endpoints';

@customElement('room-info')
export class RoomInfo extends LitElement {
  _sensorId: string = '';
  
  @state()
  info?: SensorInfo;
  
  @property({ type: String, reflect: true, attribute: 'sensor-id' })
  set sensorId(sensorId: string) {
    this._sensorId = sensorId;
    SensorEndpoint.getSensorInfo(sensorId).then((info) => (this.info = info));
  }

  get sensorId() {
    return this.sensorId;
  }

  connectedCallback() {
    super.connectedCallback();
    this.classList.add('room-info');
    this.style.display = 'block';
  }

  render() {
    return html`
      <j-card>
        <div>Temperature: ${this.info?.temperature}</div>
        <div>Target: ${this.info?.target}</div>
        <vaadin-chart type="line" no-legend>
          <vaadin-chart-series .values=${this.info?.history}></vaadin-chart-series>
        </vaadin-chart>
      </j-card>
    `;
  }
}
