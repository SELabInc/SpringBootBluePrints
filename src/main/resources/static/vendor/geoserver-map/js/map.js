/**
 * 지도 생성 js
 */
let eventSource = "";
let view;
let map;

$(function () {
    const baseLayer = openStreetMapLayerGenerator();

    map = mapGenerator(baseLayer);

    //const static_layer = staticLayerGenerator('korea_sigungu');

    //addLayerToMap(static_layer, 'static_layer');

    //map.on('singleclick', singleClickCallBackFun);

    //moveZoomLevel();
});

const openStreetMapLayerGenerator = () => {
    return new ol.layer.Tile({
        title: 'open_street',
        layerId: 'open-Street-Map',
        visible: true,
        type: 'base',
        zIndex: 1,
        source: new ol.source.OSM()
    })
}

const vworldBaseLayersGenerator = () => {
    const baseLayerNames = ['Base', 'gray', 'Satellite', 'midnight'];
    let baseLayers = [];

    for (const baseLayerIdx in baseLayerNames) {
        const baseLayerName = baseLayerNames[baseLayerIdx];
        let fileExtension = '.png';

        if (baseLayerName === 'Satellite') {
            fileExtension = '.jpeg'
        }

        baseLayers.push(new ol.layer.Tile({
                title: 'vworld_'.concat(baseLayerName),
                layerId: 'vworld-'.concat(baseLayerName, '-Map'),
                visible: true,
                type: 'base',
                zIndex: 1,
                source: new ol.source.XYZ({
                    url: 'http://api.vworld.kr/req/wmts/1.0.0/'.concat(apiKey, "/", baseLayerName, "/{z}/{y}/{x}", fileExtension),
                    crossOrigin: 'anonymous'
                })
            })
        );
        baseLayers[baseLayerIdx].set("name", baseLayers[baseLayerIdx].A.title);
    }

    return baseLayers;
}

const mapGenerator = (baseLayer) => {
    const map = new ol.Map({
        target: 'map',
        layers: [
            baseLayer
        ],
        view: new ol.View({
            center: ol.proj.transform([126.5380517322744, 36.16792263658907], 'EPSG:4326', 'EPSG:3857'),
            zoom: 7,
            extent: ol.proj.transformExtent([85.0, 5.0, 134.0, 45.0], 'EPSG:4326', 'EPSG:3857'),
            maxZoom: 18,
            minZoom: 3
        })
    });
    view = map.getView();
    return map;
}

const staticLayerGenerator = (geoServerLayerId) => {
    const static_source_layer = new ol.source.ImageWMS({
        url: geoserverUrl + 'geoserver/landslidencam/wms',
        params: {
            'FORMAT': 'image/png',
            'VERSION': '1.1.0',
            crossOrigin: 'anonymous',
            'LAYERS': 'landslidencam:'.concat(geoServerLayerId),
        },
        serverType: 'geoserver',
        projection: 'EPSG:3857'
    });

    let static_layer = new ol.layer.Image({
        source: static_source_layer,
        layerId: 'static_layer',
        title: geoServerLayerId,
        visible: true,
        zIndex: 150
    });

    eventSource = static_source_layer;
    static_layer.set('changeStaticLayer', geoServerLayerId);

    return static_layer;
}

const updateLayerByZoomLevel = (movedZoom) => {
    const layers = map.getLayers();
    const staticLayerStr = 'static_layer';
    const layerNameExchanger = {'korea_sigungu': 'korea_dong', 'korea_dong': 'korea_sigungu'}
    layers.forEach(layer => {
        const geoServerLayerId = layerNameExchanger[layer.get('changeStaticLayer')];
        const isNotNeedToUpdate = geoServerLayerId === "korea_dong" && movedZoom < 10 || geoServerLayerId === "korea_sigungu" && movedZoom > 9;
        const isNotStaticLayer = typeof layer === "undefined" || layer.get('name') !== staticLayerStr;

        if (isNotNeedToUpdate || isNotStaticLayer) {
            return;
        }

        if (layer.get("layerId") === staticLayerStr) {
            map.removeLayer(layer);
        }

        const static_layer = staticLayerGenerator(geoServerLayerId);

        const eventName = 'singleclick';

        static_layer.set('changeStaticLayer', geoServerLayerId);

        resetMapEvent(eventName, static_layer, map);
        addLayerToMap(static_layer, staticLayerStr, map);
    })
}

const addLayerToMap = (layer, layerId) => {
    layer.set('name', layerId);
    map.addLayer(layer);
}

const resetMapEvent = (eventName) => {
    map.un(eventName, singleClickCallBackFun);
    map.on(eventName, singleClickCallBackFun);
}

const singleClickCallBackFun = (event) => {
    const viewResolution = view.getResolution();
    const url = eventSource.getFeatureInfoUrl(
        event.coordinate,
        viewResolution,
        'EPSG:3857',
        {'INFO_FORMAT': 'application/json'}
    );

    if (url) {
        fetch(url)
            .then((response) => response.json())
            .then((html) => {
                    const format = new ol.format.GeoJSON({   //포멧할 GeoJSON 객체 생성
                        featureProjection: 'EPSG:3857'
                    });
                    const properties = format.readFeatures(html)[0];
                    console.log(properties);
                }
            );
    }
}

const moveZoomLevel = () => {
    let zoom = map.getView().getZoom();
    let center = map.getView().getCenter();

    map.on('moveend', () => {
        const movedZoom = map.getView().getZoom();
        const movedCenter = map.getView().getCenter();
        if (zoom !== movedZoom) {
            updateLayerByZoomLevel(movedZoom)
            zoom = movedZoom;
        }
        if (center !== movedCenter) {
            center = movedCenter;
        }
    });
}