[toc]

### RSA算法过程

#### 缺陷

- 太大的数计算会出现溢出无法计算或者导致加解密的过程出错,暂时还没想到办法去完善
- 超过10000000的数在这大数分解这个程序中是不支持分解的,因为大树分解质数本就是一个很难得过程,没有想到比较良好的算法,我在这里使用的是穷举法.

#### 加解密

##### easyRSA类的方法

```
  // 判断n是否是素数
  public boolean isPrime(int n)
```

```
  //计算a,b的最大公因子
  public int GCD(int a, int b)
```

```
//计算b^n mod m
public BigInteger ExpMod(BigInteger b, BigInteger n, BigInteger m)
```

```
//获得序号为index的素数
public int getP(int index) 
```

```
//得到e的集合
public ArrayList<Integer> getE(int p, int q) 
```

```
//让用户选择公钥
public Integer selectE(int p, int q)
```

```
//得到私钥
public Integer getPrid(int e, int fineN)
```

##### 关键代码

```java
 //得到e的集合,也就是得到公钥
    public ArrayList<Integer> getE(int p, int q) {
        ArrayList<Integer> list = new ArrayList<>();

        Integer fineN = getfineN(p, q);
        int tempE = 2;
        while (tempE != fineN) {
            if (GCD(fineN, tempE) == 1) {
                list.add(tempE);
            }
            tempE++;
        }
        return list;
    }

   //得到私钥
    public Integer getPrid(int e, int fineN) {
        //思路是先生成一个包含着
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        int tempD = 2;
        BigInteger useE = BigInteger.valueOf(e);
        BigInteger useN = BigInteger.valueOf(fineN);
        while (tempD != fineN) {
            if (BigInteger.valueOf(tempD).multiply(useE).mod(useN).intValue() == 1) {
                list.add(tempD);
            }
            tempD++;
        }
        if (!list.isEmpty()) {
            int randomInt = random.nextInt(list.size());
            return list.get(randomInt);
        } else {
            return null;
        }
    }
```





#### 大数分解求私钥

##### 关键代码

```java
//分解大数成p和q,基本讲究的就是一个穷举法,先把所有的有可能的p和q存起来
    public HashMap brokenE(int E) {
        easyRSA easyrsa = new easyRSA();
        HashMap<Integer, Integer> map = new HashMap<>();
        //穷举法算不了太大的E
        if (E < 10000000) {
            //10000000开平方不超过一万,若其有pq=e,则一定有一个p小于其开平方,不可能两个都大于其平方,这就是原理
            for (int i = 2; i < 10000; i++) {
                if (i > E) {
                    break;
                }
                for (int j = 2; j < 10000; j++) {
                    //提前退出循环,适用于E很小的情况
                    if (j > E) {
                        break;
                    }
                    if (E == i * j) {
                        //判断是否为素数
                        if (easyrsa.isPrime(i) && easyrsa.isPrime(j)) {
                            map.put(i, j);
                        }
                    }
                }
            }
        } else {
            System.out.println("此E太大,无法计算");
            return null;
        }
        if (!map.isEmpty()) {
            return map;
        }else {
            System.out.println("找不到对应的p和q");
            return null;
        }
    }
```



