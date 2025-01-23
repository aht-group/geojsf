import {GeoJSF} from './geo';
import * as ol from 'ol';
import * as olSource from 'ol/source';
import * as olStyle from 'ol/style';
import * as olProj from 'ol/proj';
import * as olLayer from 'ol/layer';
import * as olControl from 'ol/control';
import * as olExtent from 'ol/extent';
import * as olGeom from 'ol/geom';
import * as olSphere from 'ol/sphere';
import * as olInteraction from 'ol/interaction';
import * as olCoordinate from 'ol/coordinate';
import { GeoJsfViewport } from './viewport';
import { GeoJsfUtil } from './geo-util';

console.log("This ist GeoJSF 3 ... using OpenLayers version " +ol.VERSION);

declare global {
    const ol: typeof import('ol');  
    const olSource: typeof import('ol/source');
    const olStyle: typeof import('ol/style');
    const olProj: typeof import('ol/proj');
    const olLayer: typeof import('ol/layer');
    const olControl: typeof import('ol/control');
    const olExtent: typeof import('ol/extent');
    const olGeom: typeof import('ol/geom');
    const olSphere: typeof import('ol/sphere');
    const olInteraction: typeof import('ol/interaction');
    const olCoordinate: typeof import('ol/coordinate');
  }
(window as any).ol = ol;
(window as any).olControl = olControl;
(window as any).olSource = olSource;
(window as any).olStyle = olStyle;
(window as any).olProj = olProj;
(window as any).olLayer = olLayer;
(window as any).olExtent = olExtent;
(window as any).olGeom = olGeom;
(window as any).olSphere = olSphere;
(window as any).olInteraction = olInteraction;
(window as any).olCoordinate = olCoordinate;

(window as any).GeoJsfUtil = GeoJsfUtil;
(window as any).GeoJSF = GeoJSF;
(window as any).GeoJsfViewport = GeoJsfViewport;