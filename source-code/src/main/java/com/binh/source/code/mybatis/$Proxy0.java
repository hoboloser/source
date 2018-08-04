//package com.binh.source.code.mybatis;
//
//import com.binh.source.code.mybatis.TestMoService;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.lang.reflect.UndeclaredThrowableException;
//
///**
// * 生成的代理类，继承了Proxy并实现了原接口
// * @author binh
// * @date 2018/07/26
// */
//public final class $Proxy0 extends Proxy
//  implements TestMoService
//{
//   //变量，都是private static Method  XXX
//  private static Method m1;
//  private static Method m3;
//  private static Method m2;
//  private static Method m0;
//  /**
//   * 代理类的构造函数，其参数正是是InvocationHandler实例，Proxy.newInstance方法就是通过通过这个构造函数来创建代理实例的
//   */
//  public $Proxy0(InvocationHandler paramInvocationHandler)
//    throws 
//  {
//    super(paramInvocationHandler);
//  }
//  /**
//   * Object的equals方法
//   */
//  public final boolean equals(Object paramObject)
//    throws 
//  {
//    try
//    {
//      return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
//    }
//    catch (Error|RuntimeException localError)
//    {
//      throw localError;
//    }
//    catch (Throwable localThrowable)
//    {
//      throw new UndeclaredThrowableException(localThrowable);
//    }
//  }
//
//  /**
//   * 接口代理方法，调用的正是通过指定的InvocationHandler的invoke方法
//   */
//  public final void print()
//    throws 
//  {
//    try
//    {
//      this.h.invoke(this, m3, null);
//      return;
//    }
//    catch (Error|RuntimeException localError)
//    {
//      throw localError;
//    }
//    catch (Throwable localThrowable)
//    {
//      throw new UndeclaredThrowableException(localThrowable);
//    }
//  }
//  /**
//   * Object的toString方法
//   */
//  public final String toString()
//    throws 
//  {
//    try
//    {
//      return (String)this.h.invoke(this, m2, null);
//    }
//    catch (Error|RuntimeException localError)
//    {
//      throw localError;
//    }
//    catch (Throwable localThrowable)
//    {
//      throw new UndeclaredThrowableException(localThrowable);
//    }
//  }
//  /**
//   * Object的hashCode方法
//   */
//  public final int hashCode()
//    throws 
//  {
//    try
//    {
//      return ((Integer)this.h.invoke(this, m0, null)).intValue();
//    }
//    catch (Error|RuntimeException localError)
//    {
//      throw localError;
//    }
//    catch (Throwable localThrowable)
//    {
//      throw new UndeclaredThrowableException(localThrowable);
//    }
//  }
//
// /**
//  * 对变量进行一些初始化工作
//  */
//  static
//  {
//    try
//    {
//      m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
//      m3 = Class.forName("com.binh.source.code.mybatis.TestMoService").getMethod("print", new Class[0]);
//      m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
//      m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
//      return;
//    }
//    catch (NoSuchMethodException localNoSuchMethodException)
//    {
//      throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
//    }
//    catch (ClassNotFoundException localClassNotFoundException)
//    {
//      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
//    }
//  }
//}