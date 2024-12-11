class Utils {
  public getNestedValue = (v: any, n: string) => {
    const arr = n.split(".");
    let va = v;
    for (let i = 0; i < arr.length; i++) {
      const el = arr[i];
      va = va[el];
    }
    return va;
  };
}

const utils = new Utils();

export default utils;


export const USER_TYPE = localStorage.getItem("userType");
export const BASE_URL = "http://10.0.0.189:5050";

