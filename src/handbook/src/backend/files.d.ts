declare module "*.svg" {
    export const ReactComponent: React.FunctionComponent<React.SVGAttributes<SVGElement>>;
}

declare module "*.csv" {
    const content: any[];
    export default content;
}
