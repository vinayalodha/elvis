package io.github.vinayalodha.elvis.plugin;

import com.google.auto.service.AutoService;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;

/**
 * Plugin class implementation
 *
 * @author <a href="http://github.com/vinay-lodha">Vinay Lodha</a>
 */
@AutoService(Plugin.class)
public class ElvisPlugin implements Plugin {

    @Override
    public String getName() {
        return "ElvisPlugin";
    }

    @Override
    public void init(final JavacTask task, String... args) {
        task.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {
                TaskListener.super.started(e);
            }

            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() == TaskEvent.Kind.PARSE) {
                    e.getCompilationUnit().accept(new ParseTreeScanner(task, e.getCompilationUnit()), null);
                } else if (e.getKind() == TaskEvent.Kind.ANALYZE) {
                    e.getCompilationUnit().accept(new AnalyzeTreeScanner(task, e.getCompilationUnit()), null);
                }
            }
        });
    }
}
