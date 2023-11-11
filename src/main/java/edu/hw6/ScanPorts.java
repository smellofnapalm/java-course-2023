package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ScanPorts {
    final static private int LOWER_PORT = 0;
    final static private int UPPER_PORT = 49151;
    final static private Map<Integer, String> SERVICE_ON_PORT = Map.of(
        135, "EPMAP",
        137, "Служба имен NetBIOS",
        138, "Служба датаграмм NetBIOS",
        139, "Служба сеансов NetBIOS",
        445, "Microsoft-DS Active Directory",
        1900, "Simple Service Discovery Protocol (SSDP)"
    );

    public static int[] getAllBusyPorts() {
        List<Integer> result = new ArrayList<>();
        for (int port = LOWER_PORT; port <= UPPER_PORT; port++) {
            try (var socket = new ServerSocket(port); var datagram = new DatagramSocket(port)) {
                socket.isClosed();
            } catch (IOException ignored) {
                result.add(port);
            }
        }
        return result.stream().mapToInt(x -> x).toArray();
    }

    public static List<String> getInfoAboutAllBusyPorts() {
        int[] ports = getAllBusyPorts();
        List<String> res = new ArrayList<>();
        for (int port : ports) {
            try (var tcp = new ServerSocket(port)) {
                tcp.isClosed();
            } catch (IOException e) {
                res.add("TCP %d %s".formatted(port, SERVICE_ON_PORT.getOrDefault(port, "")));
            }

            try (var udp = new DatagramSocket(port)) {
                udp.isClosed();
            } catch (IOException e) {
                res.add("UDP %d %s".formatted(port, SERVICE_ON_PORT.getOrDefault(port, "")));
            }
        }
        return res;
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void prettyPrint(List<String> portsInfo) {
        final String format = "%-10s %-8s %s\n";
        System.out.printf(format, "Протокол", "Порт", "Сервис");
        for (var info : portsInfo) {
            System.out.printf(format, Arrays.stream(info.split(" ", 1 + 2)).toArray());
        }
    }

    private ScanPorts() {
    }
}
