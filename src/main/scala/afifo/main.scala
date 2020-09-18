package afifo

import chisel3._
import chisel3.Driver._

object MainApp extends App {
    chisel3.Driver.execute(args, () => new AsyncFIFO(UInt(32.W), (1 << 4)))
}

