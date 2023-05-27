import { LitElement, html } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('dashboard-view')
export class DashboardView extends LitElement {
  private recentMonthsCategories: string[] = ['Jun', 'Jul', 'Aug'];
  private recentMonthsValues: number[] = [560, 850, 722];

  render() {
    return html`
      <style>
        j-card {
          display: inline-flex;
          width: 300px;
          height: 250px;
        }
        vaadin-chart {
          height: 200px;
        }
      </style>
      <j-card>
        <span slot="title">Current consumption</span>
        <h1>1000 W</h1>
        <h3>Average: 2512 W</h3>
      </j-card>
      <j-card>
        <div slot="title">Recent months</div>
        <vaadin-chart
          .categories=${this.recentMonthsCategories}
          no-legend="false"
          .additionalOptions=${{ yAxis: { title: { text: 'Consumption (kWh)' } } }}
        >
          <vaadin-chart-series type="column" .values=${this.recentMonthsValues}></vaadin-chart-series>
        </vaadin-chart>
      </j-card>
      <vaadin-button @click=${() => console.log('hello')}>hello</vaadin-button>
      <room-info sensor-id="foo"></room-info>
    `;
  }
}
