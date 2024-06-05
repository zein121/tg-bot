package students.javabot.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import students.javabot.Model.Anecdote;
import students.javabot.Repository.AnecdoteRepository;
import students.javabot.exceptions.AnecdoteNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnecdoteServiceHTTPImpl implements AnecdoteServiceHTTP {

    private final AnecdoteRepository anecdoteRepository;

    @Override
    public Anecdote createAnecdote(Anecdote anecdote) {
        return anecdoteRepository.save(anecdote);
    }

    @Override
    public List<Anecdote> getAllAnecdotes() {
        return anecdoteRepository.findAll();
    }

    @Override
    public Optional<Anecdote> getAnecdoteById(Long id) throws AnecdoteNotFoundException {
        Optional<Anecdote> optionalAnecdote = anecdoteRepository.findById(id);
        if (optionalAnecdote.isPresent()) {
            return optionalAnecdote;
        } else {
            throw new AnecdoteNotFoundException(id);
        }
    }

    @Override
    public void deleteAnecdoteById(Long id) throws AnecdoteNotFoundException {
        Optional<Anecdote> optionalAnecdote = anecdoteRepository.findById(id);
        if (optionalAnecdote.isPresent()) {
            anecdoteRepository.delete(optionalAnecdote.get());
        } else {
            throw new AnecdoteNotFoundException(id);
        }
    }

    @Override
    public Optional<Anecdote> updateAnecdoteById(Long id, Anecdote newAnecdote) throws AnecdoteNotFoundException {
        Optional<Anecdote> optionalAnecdote = anecdoteRepository.findById(id);
        if (optionalAnecdote.isPresent()) {
            Anecdote oldAnecdote = optionalAnecdote.get();
            oldAnecdote.setDateOfUpdate(new Date());
            oldAnecdote.setText(oldAnecdote.getText());
            anecdoteRepository.save(oldAnecdote);
            return anecdoteRepository.findById(id);
        } else {
            throw new AnecdoteNotFoundException(id);
        }
    }

    @Override
    public Anecdote randomAnecdote() {
        long count = anecdoteRepository.count();
        long index = (long) (Math.random() * count);
        Optional<Anecdote> optionalAnecdote = anecdoteRepository.getAnecdoteById(index);
        return optionalAnecdote.orElse(null);
    }

    @Override
    public Object randomAnecdotes(int count) {
        List<Long> list = new ArrayList<>();
        if (anecdoteRepository.count() >= count) {
            while (list.size() < count) {
                long index = (long) (Math.random() * anecdoteRepository.count()) + 1;
                if (!list.contains(index)) {
                    Optional<Anecdote> anecdote = anecdoteRepository.getAnecdoteById(index);
                    if (anecdote.isPresent()) {
                        list.add(index);
                    }
                }
            }
            List<Anecdote> anecdoteList = new ArrayList<>(List.of());
            for (Long id : list) {
                Optional<Anecdote> optionalAnecdote = anecdoteRepository.getAnecdoteById(id);
                anecdoteList.add(optionalAnecdote.get());
            }
            return anecdoteList;
        }
        return "Anecdotes count < " + count;
    }

}
