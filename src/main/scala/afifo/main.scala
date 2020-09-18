package afifo

import chisel3._
import chisel3.Driver._

class X extends Bundle {
    val b = Bool()
    val a = UInt(4.W)
}

object MainApp extends App {
    chisel3.Driver.execute(args, () => new AsyncFIFO(new X, 8))
}

