///**
// * Copyright (c) 
// * 
// * Revision History
// *
// * Date            Programmer              Notes
// * ---------    ---------------------  --------------------------------------------
// * 2018/07/29	       binh              Initial
// */
//package com.binh.source.code.mybatis.impl;
//
///**
// * @ClassName @{link CglibProxyClass}
// * @Description TODO
// *
// * @author binh
// * @date 2018/07/29
// */
//package com.binh.source.code.mybatis.impl;
//
//import com.binh.source.code.domain.People;
//import java.lang.reflect.Method;
//import java.util.List;
//import net.sf.cglib.core.ReflectUtils;
//import net.sf.cglib.core.Signature;
//import net.sf.cglib.proxy.Callback;
//import net.sf.cglib.proxy.Factory;
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;
//
//public class PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef extends PeopleServiceImpl
//  implements Factory
//{
//  private boolean CGLIB$BOUND;
//  public static Object CGLIB$FACTORY_DATA;
//  private static final ThreadLocal CGLIB$THREAD_CALLBACKS;
//  private static final Callback[] CGLIB$STATIC_CALLBACKS;
//  private MethodInterceptor CGLIB$CALLBACK_0;
//  private static Object CGLIB$CALLBACK_FILTER;
//  private static final Method CGLIB$update$0$Method;
//  private static final MethodProxy CGLIB$update$0$Proxy;
//  private static final Object[] CGLIB$emptyArgs;
//  private static final Method CGLIB$insert$1$Method;
//  private static final MethodProxy CGLIB$insert$1$Proxy;
//  private static final Method CGLIB$listPeoples$2$Method;
//  private static final MethodProxy CGLIB$listPeoples$2$Proxy;
//  private static final Method CGLIB$listPeoplesSecondCache$3$Method;
//  private static final MethodProxy CGLIB$listPeoplesSecondCache$3$Proxy;
//  private static final Method CGLIB$listPeoplesFromMapper$4$Method;
//  private static final MethodProxy CGLIB$listPeoplesFromMapper$4$Proxy;
//  private static final Method CGLIB$insertMapper$5$Method;
//  private static final MethodProxy CGLIB$insertMapper$5$Proxy;
//  private static final Method CGLIB$listPeoplesCache$6$Method;
//  private static final MethodProxy CGLIB$listPeoplesCache$6$Proxy;
//  private static final Method CGLIB$updateMapper$7$Method;
//  private static final MethodProxy CGLIB$updateMapper$7$Proxy;
//  private static final Method CGLIB$getPeopleById$8$Method;
//  private static final MethodProxy CGLIB$getPeopleById$8$Proxy;
//  private static final Method CGLIB$equals$9$Method;
//  private static final MethodProxy CGLIB$equals$9$Proxy;
//  private static final Method CGLIB$toString$10$Method;
//  private static final MethodProxy CGLIB$toString$10$Proxy;
//  private static final Method CGLIB$hashCode$11$Method;
//  private static final MethodProxy CGLIB$hashCode$11$Proxy;
//  private static final Method CGLIB$clone$12$Method;
//  private static final MethodProxy CGLIB$clone$12$Proxy;
//
//  public PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef()
//  {
//    CGLIB$BIND_CALLBACKS(this);
//  }
//
//  static
//  {
//    CGLIB$STATICHOOK1();
//  }
//
//  public final boolean equals(Object paramObject)
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//    {
//      Object tmp41_36 = tmp17_14.intercept(this, CGLIB$equals$9$Method, new Object[] { paramObject }, CGLIB$equals$9$Proxy);
//      tmp41_36;
//      return tmp41_36 == null ? false : ((Boolean)tmp41_36).booleanValue();
//    }
//    return super.equals(paramObject);
//  }
//
//  public final String toString()
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//      return (String)tmp17_14.intercept(this, CGLIB$toString$10$Method, CGLIB$emptyArgs, CGLIB$toString$10$Proxy);
//    return super.toString();
//  }
//
//  public final int hashCode()
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//    {
//      Object tmp36_31 = tmp17_14.intercept(this, CGLIB$hashCode$11$Method, CGLIB$emptyArgs, CGLIB$hashCode$11$Proxy);
//      tmp36_31;
//      return tmp36_31 == null ? 0 : ((Number)tmp36_31).intValue();
//    }
//    return super.hashCode();
//  }
//
//  protected final Object clone()
//    throws CloneNotSupportedException
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//      return tmp17_14.intercept(this, CGLIB$clone$12$Method, CGLIB$emptyArgs, CGLIB$clone$12$Proxy);
//    return super.clone();
//  }
//
//  public final int update(People paramPeople)
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//    {
//      Object tmp41_36 = tmp17_14.intercept(this, CGLIB$update$0$Method, new Object[] { paramPeople }, CGLIB$update$0$Proxy);
//      tmp41_36;
//      return tmp41_36 == null ? 0 : ((Number)tmp41_36).intValue();
//    }
//    return super.update(paramPeople);
//  }
//
//  public Object newInstance(Class[] paramArrayOfClass, Object[] paramArrayOfObject, Callback[] paramArrayOfCallback)
//  {
//    CGLIB$SET_THREAD_CALLBACKS(paramArrayOfCallback);
//    Class[] tmp9_8 = paramArrayOfClass;
//    switch (tmp9_8.length)
//    {
//    case 0:
//      tmp9_8;
//      break;
//    default:
//      new 75cab4ef();
//      throw new IllegalArgumentException("Constructor not found");
//    }
//    CGLIB$SET_THREAD_CALLBACKS(null);
//  }
//
//  public Object newInstance(Callback paramCallback)
//  {
//    CGLIB$SET_THREAD_CALLBACKS(new Callback[] { paramCallback });
//    CGLIB$SET_THREAD_CALLBACKS(null);
//    return new 75cab4ef();
//  }
//
//  public Object newInstance(Callback[] paramArrayOfCallback)
//  {
//    CGLIB$SET_THREAD_CALLBACKS(paramArrayOfCallback);
//    CGLIB$SET_THREAD_CALLBACKS(null);
//    return new 75cab4ef();
//  }
//
//  public final int insert(People paramPeople)
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//    {
//      Object tmp41_36 = tmp17_14.intercept(this, CGLIB$insert$1$Method, new Object[] { paramPeople }, CGLIB$insert$1$Proxy);
//      tmp41_36;
//      return tmp41_36 == null ? 0 : ((Number)tmp41_36).intValue();
//    }
//    return super.insert(paramPeople);
//  }
//
//  public final List listPeoples()
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//      return (List)tmp17_14.intercept(this, CGLIB$listPeoples$2$Method, CGLIB$emptyArgs, CGLIB$listPeoples$2$Proxy);
//    return super.listPeoples();
//  }
//
//  public void setCallback(int paramInt, Callback paramCallback)
//  {
//    switch (paramInt)
//    {
//    case 0:
//      this.CGLIB$CALLBACK_0 = ((MethodInterceptor)paramCallback);
//      break;
//    }
//  }
//
//  public void setCallbacks(Callback[] paramArrayOfCallback)
//  {
//    this.CGLIB$CALLBACK_0 = ((MethodInterceptor)paramArrayOfCallback[0]);
//  }
//
//  public Callback getCallback(int paramInt)
//  {
//    CGLIB$BIND_CALLBACKS(this);
//    switch (paramInt)
//    {
//    case 0:
//      break;
//    }
//    return null;
//  }
//
//  public Callback[] getCallbacks()
//  {
//    CGLIB$BIND_CALLBACKS(this);
//    return new Callback[] { this.CGLIB$CALLBACK_0 };
//  }
//
//  public static void CGLIB$SET_STATIC_CALLBACKS(Callback[] paramArrayOfCallback)
//  {
//    CGLIB$STATIC_CALLBACKS = paramArrayOfCallback;
//  }
//
//  public static void CGLIB$SET_THREAD_CALLBACKS(Callback[] paramArrayOfCallback)
//  {
//    CGLIB$THREAD_CALLBACKS.set(paramArrayOfCallback);
//  }
//
//  final int CGLIB$insert$1(People paramPeople)
//  {
//    return super.insert(paramPeople);
//  }
//
//  final boolean CGLIB$equals$9(Object paramObject)
//  {
//    return super.equals(paramObject);
//  }
//
//  final int CGLIB$update$0(People paramPeople)
//  {
//    return super.update(paramPeople);
//  }
//
//  final Object CGLIB$clone$12()
//    throws CloneNotSupportedException
//  {
//    return super.clone();
//  }
//
//  static void CGLIB$STATICHOOK1()
//  {
//    CGLIB$THREAD_CALLBACKS = new ThreadLocal();
//    CGLIB$emptyArgs = new Object[0];
//    Class localClass1 = Class.forName("com.binh.source.code.mybatis.impl.PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef");
//    Class localClass2;
//    Method[] tmp84_81 = ReflectUtils.findMethods(new String[] { "equals", "(Ljava/lang/Object;)Z", "toString", "()Ljava/lang/String;", "hashCode", "()I", "clone", "()Ljava/lang/Object;" }, (localClass2 = Class.forName("java.lang.Object")).getDeclaredMethods());
//    CGLIB$equals$9$Method = tmp84_81[0];
//    CGLIB$equals$9$Proxy = MethodProxy.create(localClass2, localClass1, "(Ljava/lang/Object;)Z", "equals", "CGLIB$equals$9");
//    Method[] tmp105_84 = tmp84_81;
//    CGLIB$toString$10$Method = tmp105_84[1];
//    CGLIB$toString$10$Proxy = MethodProxy.create(localClass2, localClass1, "()Ljava/lang/String;", "toString", "CGLIB$toString$10");
//    Method[] tmp126_105 = tmp105_84;
//    CGLIB$hashCode$11$Method = tmp126_105[2];
//    CGLIB$hashCode$11$Proxy = MethodProxy.create(localClass2, localClass1, "()I", "hashCode", "CGLIB$hashCode$11");
//    Method[] tmp147_126 = tmp126_105;
//    CGLIB$clone$12$Method = tmp147_126[3];
//    CGLIB$clone$12$Proxy = MethodProxy.create(localClass2, localClass1, "()Ljava/lang/Object;", "clone", "CGLIB$clone$12");
//    tmp147_126;
//    Method[] tmp308_305 = ReflectUtils.findMethods(new String[] { "update", "(Lcom/binh/source/code/domain/People;)I", "insert", "(Lcom/binh/source/code/domain/People;)I", "listPeoples", "()Ljava/util/List;", "listPeoplesSecondCache", "()Ljava/util/List;", "listPeoplesFromMapper", "()Ljava/util/List;", "insertMapper", "(Lcom/binh/source/code/domain/People;)I", "listPeoplesCache", "()Ljava/util/List;", "updateMapper", "(Lcom/binh/source/code/domain/People;)I", "getPeopleById", "(Ljava/lang/Long;)Lcom/binh/source/code/domain/People;" }, (localClass2 = Class.forName("com.binh.source.code.mybatis.impl.PeopleServiceImpl")).getDeclaredMethods());
//    CGLIB$update$0$Method = tmp308_305[0];
//    CGLIB$update$0$Proxy = MethodProxy.create(localClass2, localClass1, "(Lcom/binh/source/code/domain/People;)I", "update", "CGLIB$update$0");
//    Method[] tmp331_308 = tmp308_305;
//    CGLIB$insert$1$Method = tmp331_308[1];
//    CGLIB$insert$1$Proxy = MethodProxy.create(localClass2, localClass1, "(Lcom/binh/source/code/domain/People;)I", "insert", "CGLIB$insert$1");
//    Method[] tmp354_331 = tmp331_308;
//    CGLIB$listPeoples$2$Method = tmp354_331[2];
//    CGLIB$listPeoples$2$Proxy = MethodProxy.create(localClass2, localClass1, "()Ljava/util/List;", "listPeoples", "CGLIB$listPeoples$2");
//    Method[] tmp377_354 = tmp354_331;
//    CGLIB$listPeoplesSecondCache$3$Method = tmp377_354[3];
//    CGLIB$listPeoplesSecondCache$3$Proxy = MethodProxy.create(localClass2, localClass1, "()Ljava/util/List;", "listPeoplesSecondCache", "CGLIB$listPeoplesSecondCache$3");
//    Method[] tmp400_377 = tmp377_354;
//    CGLIB$listPeoplesFromMapper$4$Method = tmp400_377[4];
//    CGLIB$listPeoplesFromMapper$4$Proxy = MethodProxy.create(localClass2, localClass1, "()Ljava/util/List;", "listPeoplesFromMapper", "CGLIB$listPeoplesFromMapper$4");
//    Method[] tmp423_400 = tmp400_377;
//    CGLIB$insertMapper$5$Method = tmp423_400[5];
//    CGLIB$insertMapper$5$Proxy = MethodProxy.create(localClass2, localClass1, "(Lcom/binh/source/code/domain/People;)I", "insertMapper", "CGLIB$insertMapper$5");
//    Method[] tmp446_423 = tmp423_400;
//    CGLIB$listPeoplesCache$6$Method = tmp446_423[6];
//    CGLIB$listPeoplesCache$6$Proxy = MethodProxy.create(localClass2, localClass1, "()Ljava/util/List;", "listPeoplesCache", "CGLIB$listPeoplesCache$6");
//    Method[] tmp470_446 = tmp446_423;
//    CGLIB$updateMapper$7$Method = tmp470_446[7];
//    CGLIB$updateMapper$7$Proxy = MethodProxy.create(localClass2, localClass1, "(Lcom/binh/source/code/domain/People;)I", "updateMapper", "CGLIB$updateMapper$7");
//    Method[] tmp494_470 = tmp470_446;
//    CGLIB$getPeopleById$8$Method = tmp494_470[8];
//    CGLIB$getPeopleById$8$Proxy = MethodProxy.create(localClass2, localClass1, "(Ljava/lang/Long;)Lcom/binh/source/code/domain/People;", "getPeopleById", "CGLIB$getPeopleById$8");
//    tmp494_470;
//  }
//
//  public final List listPeoplesSecondCache()
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//      return (List)tmp17_14.intercept(this, CGLIB$listPeoplesSecondCache$3$Method, CGLIB$emptyArgs, CGLIB$listPeoplesSecondCache$3$Proxy);
//    return super.listPeoplesSecondCache();
//  }
//
//  public final List listPeoplesFromMapper()
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//      return (List)tmp17_14.intercept(this, CGLIB$listPeoplesFromMapper$4$Method, CGLIB$emptyArgs, CGLIB$listPeoplesFromMapper$4$Proxy);
//    return super.listPeoplesFromMapper();
//  }
//
//  public static MethodProxy CGLIB$findMethodProxy(Signature paramSignature)
//  {
//    String tmp4_1 = paramSignature.toString();
//    switch (tmp4_1.hashCode())
//    {
//    case -1569491869:
//      if (tmp4_1.equals("listPeoplesFromMapper()Ljava/util/List;"))
//        return CGLIB$listPeoplesFromMapper$4$Proxy;
//      break;
//    case -1471349039:
//    case -508378822:
//    case -488758591:
//    case -438144676:
//    case 284943994:
//    case 728030610:
//    case 1266577912:
//    case 1549459726:
//    case 1658566018:
//    case 1826985398:
//    case 1913648695:
//    case 1984935277:
//    }
//  }
//
//  public final int insertMapper(People paramPeople)
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//    {
//      Object tmp41_36 = tmp17_14.intercept(this, CGLIB$insertMapper$5$Method, new Object[] { paramPeople }, CGLIB$insertMapper$5$Proxy);
//      tmp41_36;
//      return tmp41_36 == null ? 0 : ((Number)tmp41_36).intValue();
//    }
//    return super.insertMapper(paramPeople);
//  }
//
//  public final List listPeoplesCache()
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//      return (List)tmp17_14.intercept(this, CGLIB$listPeoplesCache$6$Method, CGLIB$emptyArgs, CGLIB$listPeoplesCache$6$Proxy);
//    return super.listPeoplesCache();
//  }
//
//  public final int updateMapper(People paramPeople)
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//    {
//      Object tmp41_36 = tmp17_14.intercept(this, CGLIB$updateMapper$7$Method, new Object[] { paramPeople }, CGLIB$updateMapper$7$Proxy);
//      tmp41_36;
//      return tmp41_36 == null ? 0 : ((Number)tmp41_36).intValue();
//    }
//    return super.updateMapper(paramPeople);
//  }
//
//  public final People getPeopleById(Long paramLong)
//  {
//    MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
//    if (tmp4_1 == null)
//    {
//      tmp4_1;
//      CGLIB$BIND_CALLBACKS(this);
//    }
//    MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
//    if (tmp17_14 != null)
//      return (People)tmp17_14.intercept(this, CGLIB$getPeopleById$8$Method, new Object[] { paramLong }, CGLIB$getPeopleById$8$Proxy);
//    return super.getPeopleById(paramLong);
//  }
//
//  private static final void CGLIB$BIND_CALLBACKS(Object paramObject)
//  {
//    // Byte code:
//    //   0: aload_0
//    //   1: checkcast 2 com/binh/source/code/mybatis/impl/PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef
//    //   4: astore_1
//    //   5: aload_1
//    //   6: getfield 310    com/binh/source/code/mybatis/impl/PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef:CGLIB$BOUND  Z
//    //   9: ifne +43 -> 52
//    //   12: aload_1
//    //   13: iconst_1
//    //   14: putfield 310   com/binh/source/code/mybatis/impl/PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef:CGLIB$BOUND  Z
//    //   17: getstatic 27   com/binh/source/code/mybatis/impl/PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef:CGLIB$THREAD_CALLBACKS   Ljava/lang/ThreadLocal;
//    //   20: invokevirtual 313  java/lang/ThreadLocal:get   ()Ljava/lang/Object;
//    //   23: dup
//    //   24: ifnonnull +15 -> 39
//    //   27: pop
//    //   28: getstatic 308  com/binh/source/code/mybatis/impl/PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef:CGLIB$STATIC_CALLBACKS   [Lnet/sf/cglib/proxy/Callback;
//    //   31: dup
//    //   32: ifnonnull +7 -> 39
//    //   35: pop
//    //   36: goto +16 -> 52
//    //   39: checkcast 314  [Lnet/sf/cglib/proxy/Callback;
//    //   42: aload_1
//    //   43: swap
//    //   44: iconst_0
//    //   45: aaload
//    //   46: checkcast 52   net/sf/cglib/proxy/MethodInterceptor
//    //   49: putfield 40    com/binh/source/code/mybatis/impl/PeopleServiceImpl$$EnhancerByCGLIB$$75cab4ef:CGLIB$CALLBACK_0 Lnet/sf/cglib/proxy/MethodInterceptor;
//    //   52: return
//  }
//
//  final int CGLIB$hashCode$11()
//  {
//    return super.hashCode();
//  }
//
//  final List CGLIB$listPeoples$2()
//  {
//    return super.listPeoples();
//  }
//
//  final List CGLIB$listPeoplesFromMapper$4()
//  {
//    return super.listPeoplesFromMapper();
//  }
//
//  final List CGLIB$listPeoplesSecondCache$3()
//  {
//    return super.listPeoplesSecondCache();
//  }
//
//  final People CGLIB$getPeopleById$8(Long paramLong)
//  {
//    return super.getPeopleById(paramLong);
//  }
//
//  final String CGLIB$toString$10()
//  {
//    return super.toString();
//  }
//
//  final int CGLIB$updateMapper$7(People paramPeople)
//  {
//    return super.updateMapper(paramPeople);
//  }
//
//  final int CGLIB$insertMapper$5(People paramPeople)
//  {
//    return super.insertMapper(paramPeople);
//  }
//
//  final List CGLIB$listPeoplesCache$6()
//  {
//    return super.listPeoplesCache();
//  }
//}