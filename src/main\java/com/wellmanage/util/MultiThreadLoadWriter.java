package com.wellmanage.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadLoadWriter extends AbstractLoadWriter {

	@Override
	public void writeContent(byte[] fileContent, String fileName) {
		final int partSize = (1024 * 1024 * 10);
		int part = fileContent.length / partSize;

		final List<Callable<Boolean>> partitions = new ArrayList<Callable<Boolean>>();

		for (int i = 0; i < part; i++) {
			final int lower = (i * partSize);
			final int upper = (i == part - 1) ? fileContent.length : lower + partSize;

			partitions.add(new Callable<Boolean>() {
				@Override public Boolean call() throws Exception {
					byte[] content = new byte[(upper - lower)];
					System.arraycopy(fileContent, lower, content, 0, (upper - lower));

					try {
						writeFile(content, String.format("data.out.%s.txt", Thread.currentThread().getName()));
					} catch (Exception e) {
						System.out.println(e.getMessage());
						return false;
					}
					return true;
				}
			});

		} //
		try {
			ExecutorService exec = Executors.newFixedThreadPool(4);
			List<Future<Boolean>> results = exec.invokeAll(partitions);
			exec.shutdown();

			for (Future<Boolean> w : results) {
				try {
					w.get();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}//

}
