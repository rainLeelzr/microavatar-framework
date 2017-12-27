package microavatar.framework.core.serialization;

import microavatar.framework.core.serialization.impl.Protobuf2Serializer;
import org.springframework.context.annotation.Import;

@Import({Protobuf2Serializer.class})
public class SerializerStarter {
}
