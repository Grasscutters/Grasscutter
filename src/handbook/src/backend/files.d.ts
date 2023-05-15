declare module "*.svg" {
    export const ReactComponent: React.FunctionComponent<React.SVGAttributes<SVGElement>>;
}

declare module "*.webp" {
    const ref: string;
    export default ref;
}

declare module "*.csv" {
    const content: any[];
    export default content;
}
